<script setup lang="ts">
import { ref, computed, onMounted, watch, defineEmits } from 'vue'
import Record_Simple from '../been/Record_Simple.vue'
import Music_Detail from '../been/Music_Detail.vue'
import { api } from '../../utils/api.ts'
import type { MusicItem, MusicStatus } from '../../types/music.ts'

const props = defineProps<{
  musicItem?: any
}>()

const emit = defineEmits(['back'])

const activeMode = ref<'genre' | 'mybest' | 'latest'>('genre')
const secondTab = ref<'genre' | 'word' | 'level' | 'version' | 'sort'>('genre')
const latestMusic = ref<MusicItem[]>([])
const selectedMusic = ref<MusicItem | null>(null)
const scrollPositions = ref<Record<string, number>>({})

const genreOptions = [
  { value: '99', label: '所有种类' },
  { value: '101', label: '流行&动漫' },
  { value: '102', label: 'niconico＆VOCALOID™' },
  { value: '103', label: '东方Project' },
  { value: '104', label: '其他游戏' },
  { value: '105', label: '舞萌' },
  { value: '106', label: '音击/中二节奏' }
]

const wordOptions = [
  { value: '0', label: 'あ行' },
  { value: '1', label: 'か行' },
  { value: '2', label: 'さ行' },
  { value: '3', label: 'た行' },
  { value: '4', label: 'な行' },
  { value: '5', label: 'は行' },
  { value: '6', label: 'ま行' },
  { value: '7', label: 'や行' },
  { value: '8', label: 'ら行' },
  { value: '9', label: 'わ行' },
  { value: '10', label: 'A～G' },
  { value: '11', label: 'H～N' },
  { value: '12', label: 'O～U' },
  { value: '13', label: 'V～Z' },
  { value: '14', label: '数字・その他' }
]

const levelOptions = [
  { value: '1', label: '等级 1' },
  { value: '2', label: '等级 2' },
  { value: '3', label: '等级 3' },
  { value: '4', label: '等级 4' },
  { value: '5', label: '等级 5' },
  { value: '6', label: '等级 6' },
  { value: '7', label: '等级 7' },
  { value: '8', label: '等级 7+' },
  { value: '9', label: '等级 8' },
  { value: '10', label: '等级 8+' },
  { value: '11', label: '等级 9' },
  { value: '12', label: '等级 9+' },
  { value: '13', label: '等级 10' },
  { value: '14', label: '等级 10+' },
  { value: '15', label: '等级 11' },
  { value: '16', label: '等级 11+' },
  { value: '17', label: '等级 12' },
  { value: '18', label: '等级 12+' },
  { value: '19', label: '等级 13' },
  { value: '20', label: '等级 13+' },
  { value: '21', label: '等级 14' },
  { value: '22', label: '等级 14+' },
  { value: '23', label: '等级 15' }
]

const versionOptions = [
  { value: '0', label: 'maimai' },
  { value: '1', label: 'maimai PLUS' },
  { value: '2', label: 'GreeN' },
  { value: '3', label: 'GreeN PLUS' },
  { value: '4', label: 'ORANGE' },
  { value: '5', label: 'ORANGE PLUS' },
  { value: '6', label: 'PiNK' },
  { value: '7', label: 'PiNK PLUS' },
  { value: '8', label: 'MURASAKi' },
  { value: '9', label: 'MURASAKi PLUS' },
  { value: '10', label: 'MiLK' },
  { value: '11', label: 'MiLK PLUS' },
  { value: '12', label: 'FiNALE' },
  { value: '13', label: '舞萌DX' },
  { value: '15', label: '舞萌DX 2021' },
  { value: '17', label: '舞萌DX 2022' },
  { value: '19', label: '舞萌DX 2023' },
  { value: '21', label: '舞萌DX 2024' },
  { value: '23', label: '舞萌DX 2025' }
]

const diffOptions = [
  { value: '0', label: 'Basic', img: 'btn_music_basic.png' },
  { value: '1', label: 'Advanced', img: 'btn_music_advanced.png' },
  { value: '2', label: 'Expert', img: 'btn_music_expert.png' },
  { value: '3', label: 'Master', img: 'btn_music_master.png' },
  { value: '4', label: 'Re:Master', img: 'btn_music_remaster.png' },
  { value: '10', label: 'Utage', img: 'btn_music_utage.png' }
]
const diffOptionsAll = [
  { value: '-1', label: 'Basic', img: 'btn_music_all.png' },
  { value: '0', label: 'Basic', img: 'btn_music_basic.png' },
  { value: '1', label: 'Advanced', img: 'btn_music_advanced.png' },
  { value: '2', label: 'Expert', img: 'btn_music_expert.png' },
  { value: '3', label: 'Master', img: 'btn_music_master.png' },
  { value: '4', label: 'Re:Master', img: 'btn_music_remaster.png' },
]
const currentDiff = ref('0')

const musicData = ref<{ [key: string]: MusicItem[] }>({})
const musicStatus = ref<MusicStatus | null>(null)
const genreOrder = ref<string[]>([])
const searchHistory = ref<{ genre?: string; word?: string; level?: string; version?: string; diff?: string }[]>([])
const isLoading = ref(false)

const outputFilters = async () => {
  const genreSelect = document.querySelector('select[name="genre"]') as HTMLSelectElement
  const wordSelect = document.querySelector('select[name="word"]') as HTMLSelectElement
  const levelSelect = document.querySelector('select[name="level"]') as HTMLSelectElement
  const versionSelect = document.querySelector('select[name="version"]') as HTMLSelectElement
  
  const filters = {
    mode: activeMode.value,
    secondTab: secondTab.value,
    diff: currentDiff.value,
    genre: genreSelect ? genreOptions.find(g => g.value === genreSelect.value)?.label : '所有种类',
    word: wordSelect ? wordOptions.find(w => w.value === wordSelect.value)?.label : 'あ行',
    level: levelSelect ? levelOptions.find(l => l.value === levelSelect.value)?.label : '等级 1',
    version: versionSelect ? versionOptions.find(v => v.value === versionSelect.value)?.label : 'maimai',
  }
  console.log('筛选条件:', filters)
  
  await loadMusicData(filters)
  
  if (activeMode.value === 'latest') {
    const allMusic = Object.values(musicData.value).flat()
    latestMusic.value = allMusic.slice(0, 10)
  }
  
  return filters
}

const loadMusicData = async (filters: any) => {
  isLoading.value = true
  try {
    const apiFilters: any = {
      genre: filters.genre === '所有种类' ? undefined : filters.genre,
      word: filters.word === 'あ行' ? undefined : filters.word,
      level: filters.level === '等级 1' ? undefined : filters.level,
      version: filters.version === 'maimai' ? undefined : filters.version,
      diff: filters.diff,
      mode: activeMode.value
    }

    
    console.log('发送给 API 的筛选条件:', apiFilters)
    
    const data = await api.getMusic(apiFilters)
    musicData.value = data.songs
    genreOrder.value = data.genreOrder || Object.keys(data.songs)
    musicStatus.value = data.musicStatus
    searchHistory.value.push(filters)
    console.log('加载的音乐数据:', data)
  } catch (error) {
    console.error('Failed to fetch music data:', error)
  } finally {
    isLoading.value = false
  }
}

const handleSecondTabChange = (tab: typeof secondTab.value) => {
  secondTab.value = tab
  outputFilters()
}

const handleDiffChange = (diff: string) => {
  currentDiff.value = diff
  outputFilters()
}

const handleGenreChange = (event: Event) => {
  const target = event.target as HTMLSelectElement
  console.log('选择的种类:', genreOptions.find(g => g.value === target.value)?.label)
  outputFilters()
}

const handleWordChange = (event: Event) => {
  const target = event.target as HTMLSelectElement
  console.log('选择的分类:', wordOptions.find(w => w.value === target.value)?.label)
  outputFilters()
}

const handleLevelChange = (event: Event) => {
  const target = event.target as HTMLSelectElement
  console.log('选择的等级:', levelOptions.find(l => l.value === target.value)?.label)
  outputFilters()
}

const handleVersionChange = (event: Event) => {
  const target = event.target as HTMLSelectElement
  console.log('选择的版本:', versionOptions.find(v => v.value === target.value)?.label)
  outputFilters()
}

const handleSortSearchChange = (event: Event) => {
  const target = event.target as HTMLSelectElement
  console.log('选择的筛选:', target.value)
  outputFilters()
}

const handleSortChange = (event: Event) => {
  const target = event.target as HTMLSelectElement
  console.log('选择的排序:', target.value)
  outputFilters()
}

const handleModeChange = async (mode: typeof activeMode.value) => {
  activeMode.value = mode
  await outputFilters()
}

const handleMusicClick = (item: MusicItem) => {
  scrollPositions.value[activeMode.value] = window.scrollY || document.documentElement.scrollTop
  selectedMusic.value = item
}

const handleBack = () => {
  const position = scrollPositions.value[activeMode.value] || 0
  emit('back')
  selectedMusic.value = null
  window.scrollTo({ top: position, behavior: 'smooth' })
}

onMounted(async () => {
  await outputFilters()
  
  if (props.musicItem) {
    selectedMusic.value = {
      chart_id: props.musicItem.chart_id,
      level_id: props.musicItem.level_id,
      level_String: props.musicItem.level_String,
      score: props.musicItem.score,
      music_name: props.musicItem.music_name,
      dx_score: props.musicItem.dx_score,
      combo_type: props.musicItem.combo_type,
      sync_type: props.musicItem.sync_type,
      score_type: props.musicItem.score_type,
      type: props.musicItem.type
    }
  }
})
</script>

<template>
  <div class="f_0">
    <div class="d_ib" v-if="!selectedMusic">
      <a href="#" @click.prevent="handleModeChange('genre')"><img src="/src/assets/music/tab_category_on.png" v-if="activeMode === 'genre'" class="w_72"><img src="/src/assets/music/tab_category.png" v-else class="w_72"></a>
      <a href="#" @click.prevent="handleModeChange('mybest')"><img src="/src/assets/music/tab_mybest_on.png" v-if="activeMode === 'mybest'" class="w_72"><img src="/src/assets/music/tab_mybest.png" v-else class="w_72"></a>
      <a href="#" @click.prevent="handleModeChange('latest')"><img src="/src/assets/music/tab_latest_on.png" v-if="activeMode === 'latest'" class="w_72"><img src="/src/assets/music/tab_latest.png" v-else class="w_72"></a>
    </div  >

    <div class="town_area" v-show="activeMode === 'genre'"  v-if="!selectedMusic">
      <div class="t_c">
        <a href="#" @click.prevent="handleSecondTabChange('genre')" class="d_ib p_3 m_5" :class="{ on_area: secondTab === 'genre' }">
          <img src="/src/assets/music/btn_music_genre.png" class="h_24">
        </a>
        <a href="#" @click.prevent="handleSecondTabChange('word')" class="d_ib p_3 m_5" :class="{ on_area: secondTab === 'word' }">
          <img src="/src/assets/music/btn_music_word.png" class="h_24">
        </a>
        <a href="#" @click.prevent="handleSecondTabChange('level')" class="d_ib p_3 m_5" :class="{ on_area: secondTab === 'level' }">
          <img src="/src/assets/music/btn_music_level.png" class="h_24">
        </a>
        <a href="#" @click.prevent="handleSecondTabChange('version')" class="d_ib p_3 m_5" :class="{ on_area: secondTab === 'version' }">
          <img src="/src/assets/music/btn_music_version.png" class="h_24">
        </a>
        <a href="#" @click.prevent="handleSecondTabChange('sort')" class="d_ib p_3 m_5" :class="{ on_area: secondTab === 'sort' }">
          <img src="/src/assets/music/btn_music_sort.png" class="h_24">
        </a>
      </div>

      <div v-if="secondTab === 'sort'">
        <table class="table_f collapse f_0">
          <tbody>
            <tr>
              <th class="col5 f_16">筛选</th>
              <td class="t_c">
                <select name="search" class="m_5 w_320 d_ib" @change="handleSortSearchChange">
                  <option value="A" selected="">全部乐曲</option>
                  <optgroup label="乐曲分类">
                    <option value="G">全部</option>
                    <option value="G-101">流行&动漫</option>
                    <option value="G-102">niconico＆VOCALOID™</option>
                    <option value="G-103">东方Project</option>
                    <option value="G-104">其他游戏</option>
                    <option value="G-105">舞萌</option>
                    <option value="G-106">音击/中二节奏</option>
                  </optgroup>
                  <optgroup label="读音">
                    <option value="W">全部</option>
                    <option value="W-0">あ行</option>
                    <option value="W-1">か行</option>
                    <option value="W-2">さ行</option>
                    <option value="W-3">た行</option>
                    <option value="W-4">な行</option>
                    <option value="W-5">は行</option>
                    <option value="W-6">ま行</option>
                    <option value="W-7">や行</option>
                    <option value="W-8">ら行</option>
                    <option value="W-9">わ行</option>
                    <option value="W-10">A～G</option>
                    <option value="W-11">H～N</option>
                    <option value="W-12">O～U</option>
                    <option value="W-13">V～Z</option>
                    <option value="W-14">数字・その他</option>
                  </optgroup>
                  <optgroup label="等级">
                    <option value="L">全部</option>
                    <option value="L-1">等级 1</option>
                    <option value="L-2">等级 2</option>
                    <option value="L-3">等级 3</option>
                    <option value="L-4">等级 4</option>
                    <option value="L-5">等级 5</option>
                    <option value="L-6">等级 6</option>
                    <option value="L-7">等级 7</option>
                    <option value="L-8">等级 7+</option>
                    <option value="L-9">等级 8</option>
                    <option value="L-10">等级 8+</option>
                    <option value="L-11">等级 9</option>
                    <option value="L-12">等级 9+</option>
                    <option value="L-13">等级 10</option>
                    <option value="L-14">等级 10+</option>
                    <option value="L-15">等级 11</option>
                    <option value="L-16">等级 11+</option>
                    <option value="L-17">等级 12</option>
                    <option value="L-18">等级 12+</option>
                    <option value="L-19">等级 13</option>
                    <option value="L-20">等级 13+</option>
                    <option value="L-21">等级 14</option>
                    <option value="L-22">等级 14+</option>
                    <option value="L-23">等级 15</option>
                  </optgroup>
                  <optgroup label="版本">
                    <option value="V">全部</option>
                    <option value="V-0">maimai</option>
                    <option value="V-1">maimai PLUS</option>
                    <option value="V-2">GreeN</option>
                    <option value="V-3">GreeN PLUS</option>
                    <option value="V-4">ORANGE</option>
                    <option value="V-5">ORANGE PLUS</option>
                    <option value="V-6">PiNK</option>
                    <option value="V-7">PiNK PLUS</option>
                    <option value="V-8">MURASAKi</option>
                    <option value="V-9">MURASAKi PLUS</option>
                    <option value="V-10">MiLK</option>
                    <option value="V-11">MiLK PLUS</option>
                    <option value="V-12">FiNALE</option>
                    <option value="V-13">舞萌DX</option>
                    <option value="V-15">舞萌DX 2021</option>
                    <option value="V-17">舞萌DX 2022</option>
                    <option value="V-19">舞萌DX 2023</option>
                    <option value="V-21">舞萌DX 2024</option>
                    <option value="V-23">舞萌DX 2025</option>
                  </optgroup>
                </select>
              </td>
            </tr>
            <tr>
              <th class="col5 f_16">排序</th>
              <td class="t_c">
                <select name="sort" class="m_5 w_320 d_ib" @change="handleSortChange">
                  <option value="1">达成率（由高到低）</option>
                  <option value="2">达成率（由低到高）</option>
                  <option value="3">DX分数（由高到低）</option>
                  <option value="4">DX分数（由低到高）</option>
                  <option value="5">AP达成（已达成）</option>
                  <option value="6">AP达成（未达成）</option>
                </select>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="m_5 f_15">
          <label class="p_r m_5">
            <input type="checkbox" name="playCheck" checked="">
            <span>未游玩乐曲除外</span>
          </label>
        </div>
      </div>

      <form v-else>
        <select name="genre" class="w_300 m_10" :class="{ 'd_ib': secondTab === 'genre' }" @change="handleGenreChange">
          <option value="99">所有种类</option>
          <option value="101">流行&动漫</option>
          <option value="102">niconico＆VOCALOID™</option>
          <option value="103">东方Project</option>
          <option value="104">其他游戏</option>
          <option value="105">舞萌</option>
          <option value="106">音击/中二节奏</option>
        </select>

        <select name="word" class="w_300 m_10" :class="{ 'd_ib': secondTab === 'word' }" @change="handleWordChange">
          <option value="0">あ行</option>
          <option value="1">か行</option>
          <option value="2">さ行</option>
          <option value="3">た行</option>
          <option value="4">な行</option>
          <option value="5">は行</option>
          <option value="6">ま行</option>
          <option value="7">や行</option>
          <option value="8">ら行</option>
          <option value="9">わ行</option>
          <option value="10">A～G</option>
          <option value="11">H～N</option>
          <option value="12">O～U</option>
          <option value="13">V～Z</option>
          <option value="14">数字・その他</option>
        </select>

        <select name="level" class="w_300 m_10" :class="{ 'd_ib': secondTab === 'level' }" @change="handleLevelChange">
          <option value="1" selected="">等级 1</option>
          <option value="2">等级 2</option>
          <option value="3">等级 3</option>
          <option value="4">等级 4</option>
          <option value="5">等级 5</option>
          <option value="6">等级 6</option>
          <option value="7">等级 7</option>
          <option value="8">等级 7+</option>
          <option value="9">等级 8</option>
          <option value="10">等级 8+</option>
          <option value="11">等级 9</option>
          <option value="12">等级 9+</option>
          <option value="13">等级 10</option>
          <option value="14">等级 10+</option>
          <option value="15">等级 11</option>
          <option value="16">等级 11+</option>
          <option value="17">等级 12</option>
          <option value="18">等级 12+</option>
          <option value="19">等级 13</option>
          <option value="20">等级 13+</option>
          <option value="21">等级 14</option>
          <option value="22">等级 14+</option>
          <option value="23">等级 15</option>
        </select>

        <select name="version" class="w_300 m_10" :class="{ 'd_ib': secondTab === 'version' }" @change="handleVersionChange">
          <option value="0">maimai</option>
          <option value="1">maimai PLUS</option>
          <option value="2">GreeN</option>
          <option value="3">GreeN PLUS</option>
          <option value="4">ORANGE</option>
          <option value="5">ORANGE PLUS</option>
          <option value="6">PiNK</option>
          <option value="7">PiNK PLUS</option>
          <option value="8">MURASAKi</option>
          <option value="9">MURASAKi PLUS</option>
          <option value="10">MiLK</option>
          <option value="11">MiLK PLUS</option>
          <option value="12">FiNALE</option>
          <option value="13">舞萌DX</option>
          <option value="15">舞萌DX 2021</option>
          <option value="17">舞萌DX 2022</option>
          <option value="19">舞萌DX 2023</option>
          <option value="21">舞萌DX 2024</option>
          <option value="23">舞萌DX 2025</option>
        </select>

        <div v-if="secondTab !== 'level'">
          <button
            v-for="diff in diffOptions"
            :key="diff.value" 
            type="button"
            :name="diff.value" 
            :value="diff.value" 
            class="p_r m_2 f_0"
            :class="{ selected: currentDiff === diff.value }"
            @click="handleDiffChange(diff.value)"
          >
            <img :src="`/src/assets/music/${diff.img}`" class="w_71">
            <img src="/src/assets/home_sub/icon_on.png" class="diffbtn_selected" v-if="currentDiff === diff.value">
          </button>
        </div>
      </form>
    </div >
    <div class="town_area" v-show="activeMode === 'mybest'" >
      <form>
        <div>
          <button
              v-for="diff in diffOptionsAll"
              :key="diff.value"
              type="button"
              :name="diff.value"
              :value="diff.value"
              class="p_r m_20 f_0"
              :class="{ selected: currentDiff === diff.value }"
              @click="handleDiffChange(diff.value)"
          >
            <img :src="`/src/assets/music/${diff.img}`" class="w_71">
            <img src="/src/assets/home_sub/icon_on.png" class="diffbtn_selected" v-if="currentDiff === diff.value">
          </button>
        </div>
      </form>
    </div>

    <div class="see_through_block m_5 m_t_10" v-show="activeMode!=='latest'"  v-if="!selectedMusic">
      <table class="music_scorelist_table table_f collapse f_0">
        <tbody>
        <tr>
          <td class="t_c"><img src="/src/assets/music/music_icon_clear.png" class="h_25"><div class="f_10">{{ musicStatus?.clearCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_s.png" class="h_24"><div class="f_10">{{ musicStatus?.sCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_sp.png" class="h_24"><div class="f_10">{{ musicStatus?.spCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_ss.png" class="h_24"><div class="f_10">{{ musicStatus?.ssCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_ssp.png" class="h_24"><div class="f_10">{{ musicStatus?.sspCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_sss.png" class="h_24"><div class="f_10">{{ musicStatus?.sssCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_sssp.png" class="h_24"><div class="f_10">{{ musicStatus?.ssspCount }}/{{ musicStatus?.randDollyCount }}</div></td>
        </tr>
        <tr>
          <td colspan="3" class="t_c"></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_fc.png" class="h_30"><div class="f_10">{{ musicStatus?.fcCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_fcp.png" class="h_30"><div class="f_10">{{ musicStatus?.fcpCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_ap.png" class="h_30"><div class="f_10">{{ musicStatus?.apCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_app.png" class="h_30"><div class="f_10">{{ musicStatus?.appCount }}/{{ musicStatus?.randDollyCount }}</div></td>
        </tr>
        <tr>
          <td colspan="2" class="t_c"></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_sync.png" class="h_30"><div class="f_10">{{ musicStatus?.syncCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_fs.png" class="h_30"><div class="f_10">{{ musicStatus?.fsCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_fsp.png" class="h_30"><div class="f_10">{{ musicStatus?.fspCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_fdx.png" class="h_30"><div class="f_10">{{ musicStatus?.fdxCount }}/{{ musicStatus?.randDollyCount }}</div></td>
          <td class="t_c"><img src="/src/assets/record/music_icon_fdxp.png" class="h_30"><div class="f_10">{{ musicStatus?.fdxpCount }}/{{ musicStatus?.randDollyCount }}</div></td>
        </tr>
        <tr>
          <td class="t_c"><div><img src="/src/assets/music/icon_rand_dolly.png" class="h_30 m_3"><div class="f_10"></div></div></td>
          <td class="t_c"></td>
          <td class="t_c"><div><img src="/src/assets/music/music_icon_dxstar_1.png" class="h_24"><div class="f_10">{{ musicStatus?.dxStar1Count }}/{{ musicStatus?.randDollyCount }}</div></div></td>
          <td class="t_c"><div><img src="/src/assets/music/music_icon_dxstar_2.png" class="h_24"><div class="f_10">{{ musicStatus?.dxStar2Count }}/{{ musicStatus?.randDollyCount }}</div></div></td>
          <td class="t_c"><div><img src="/src/assets/music/music_icon_dxstar_3.png" class="h_24"><div class="f_10">{{ musicStatus?.dxStar3Count }}/{{ musicStatus?.randDollyCount }}</div></div></td>
          <td class="t_c"><div><img src="/src/assets/music/music_icon_dxstar_4.png" class="h_24"><div class="f_10">{{ musicStatus?.dxStar4Count }}/{{ musicStatus?.randDollyCount }}</div></div></td>
          <td class="t_c"><div><img src="/src/assets/music/music_icon_dxstar_5.png" class="h_24"><div class="f_10">{{ musicStatus?.dxStar5Count }}/{{ musicStatus?.randDollyCount }}</div></div></td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="town_area" v-show="activeMode === 'latest'">
      <div v-if="!selectedMusic" class="p_t_10 p_b_10">
        <div v-for="(item, index) in latestMusic" :key="index" class="see_through_block pointer w_450 m_15 p_r p_3 f_0" @click="handleMusicClick(item)">
          <div class="clearfix"></div>
          <div class="m_5 p_5 t_l f_13 break">{{ item.music_name }}</div>
          <img v-if="item.type === 1" src="/src/assets/record/music_dx.png" class="music_kind_icon f_r">
          <img v-if="item.type === 0" src="/src/assets/record/music_standard.png" class="music_kind_icon f_r">
        </div>
      </div>
      <div v-else class="t_c">
        <button type="button" @click="handleBack" class="f_0">
          <img src="/src/assets/playlog/btn_back.png" class="w_80 m_t_10">
        </button>
      </div>
    </div>
    <div class="" v-if="activeMode!=='latest'">
      <div v-if="!selectedMusic">
        <div v-if="isLoading">加载中...</div>
        <div v-else>
          <div v-for="genre in genreOrder" :key="genre" class="p_0">
            <div class="screw_block m_15 f_15 p_s">
              {{ genre }}
            </div>
            <Record_Simple
                v-for="(item, index) in musicData[genre]"
                :key="index"
                :level_id="item.level_id"
                :level_String="item.level_String"
                :score="item.score"
                :music_name="item.music_name"
                :dx_score="item.dx_score"
                :comboType="item.combo_type"
                :syncType="item.sync_type"
                :score-type="item.score_type"
                :type="item.type"
                class="pointer"
                @click="handleMusicClick(item)"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="h_900">
      <Music_Detail v-if="selectedMusic" :chart-id="selectedMusic.chart_id"/>
      <div  v-if="selectedMusic"  class="t_c">
        <img src="/src/assets/playlog/line_02.png" alt="line" class="line_image2" />
        <button type="button" @click="handleBack" class="f_0">
          <img src="/src/assets/playlog/btn_back.png" class="w_80 m_t_10">
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.on_area {
  background: -webkit-linear-gradient(top, transparent 55%, #ffe839 45%);
  background: linear-gradient(transparent 55%, #ffe839 45%);
}
.h_900 {
  min_height: 300px;
}
.d_ib {
  display: inline-block;
}

.p_3 {
  padding: 3px;
}

.m_5 {
  margin: 5px;
}

.m_2 {
  margin: 2px;
}
.m_20 {
  margin: 2px;
  padding-top: 5px;
  padding-bottom: 5px;
}
.f_0 {
  font-size: 0;
  line-height: 0;
  background: transparent;
  border: none;
  padding: 0;
  cursor: pointer;
}
.col5 {
    width: 22%;
}
.f_13 {
  font-size: 16px;
  padding: 8px;
  line-height: 1.2rem;
}
.line_image2 {
}
.f_16 {
    font-size: 16px;
    line-height: 1.2rem;
}
.m_5 {
    margin: 5px;
}

.w_320 {
    width: 320px;
}
.w_300 {
  width: 300px;
}
.h_24 {
  height: 24px;
}
.h_30 {
  height: 30px;
}
.h_16 {
  height: 16px;
}
.w_71 {
  width: 71px;
}
.w_80 {
  width: 80px;
  top:-10px;
}
.p_r {
  position: relative;
}

.diffbtn_selected {
  position: absolute;
  top: 0;
  right: 0;
}

.town_area {
  background: #f0f0f0 url(/src/assets/music/back_town_02.png) bottom no-repeat;
}

.m_10 {
  margin: 10px;
}

.see_through_block {
  background-color: rgba(255, 255, 255, 0.6);
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border: 2px solid #000;
  border-radius: 5px;
}

.table_f {
  table-layout: fixed;
}

.collapse {
  border-collapse: collapse;
}

.music_scorelist_table tr {
  border-bottom: 1px solid #fff;
}

.music_scorelist_table td {
  padding: 9px 0;
}

.see_through_block tr:last-child td:first-child {
  text-align: left;
}

.see_through_block tr:last-child td:not(:first-child) {
  text-align: right;
}

.t_c {
  text-align: center;
}

.music_scorelist_table tr:last-child {
  border: none;
}
.h_10 {
  height: 10px;
}
select {
  padding: 8px 35px 8px 10px;
  border: 2px solid #000;
  border-radius: 10px;
  outline: none;
  vertical-align: middle;
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  background: #fff url(/src/assets/music/back_select.png) no-repeat right center;
  display: none;
}
select.d_ib {
  display: inline-block;
}
.p_r {
  position: relative;
}
.m_2 {
  margin: 2px;
  padding-bottom: 2px;
}
.f_0 {
  font-size: 0;
  line-height: 0;
}
.f_10 {
  width: 66px;
  font-size: 10px;
  text-align: center;
  bottom: 1px;
}
.diffbtn_selected {
  position: absolute;
  top: -9px;
  left: 0;
  right: 0;
  margin: auto;
}

.search_history_block {
  background-color: rgba(255, 255, 255, 0.6);
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border: 2px solid #000;
  border-radius: 5px;
}

.search_history_item {
  display: inline-block;
  padding: 5px 10px;
  margin: 5px;
  background: #fff;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.m_l_5 {
  margin-left: 5px;
}

.screw_block {
  padding: 15px 17px;
  background: url(/src/assets/home_sub/icon_screw.png) left top no-repeat, url(/src/assets/home_sub/icon_screw.png) right top no-repeat, url(/src/assets/home_sub/icon_screw.png) left bottom no-repeat, url(/src/assets/home_sub/icon_screw.png) right bottom no-repeat, #fff;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border: 1px solid #b3b3b3;
  border-radius: 5px;
}
.screw_block.sticky-active {
  position: fixed;
  top: 0;
  margin: 0;
  width: 93%;
  max-width: 440px;
  margin: 0 auto;
  box-sizing: border-box;
}
.p_s {
  position: -webkit-sticky;
  position: sticky;
  top: 0;
  z-index: 99;
}

@media (max-width: 768px) {
  .d_ib > a {
    display: inline-block;
    width: 33.33%;
    box-sizing: border-box;
  }
}

.music_kind_icon {
  position: absolute;
  top: -8px;
  right: 4px;
  height: 20px;
}

.p_t_10 {
  padding-top: 10px;
}
img {
  border: none;
  background: transparent;
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -moz-touch-callout: none;
  -moz-user-select: none;
  touch-callout: none;
  user-select: none;
}
.p_b_10 {
  padding-bottom: 10px;
}
.p_0 {
  padding: 10px;
}
</style>
