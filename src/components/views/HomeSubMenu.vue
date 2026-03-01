<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick } from 'vue';
import Record_Simple from '../Record_Simple.vue';

const homeSubItems = [
  { id: 'home', name: 'menu_sub_home_home' },
  { id: 'rating', name: 'menu_sub_home_rating' },
  { id: 'sub_home1', name: 'menu_sub_home' },
  { id: 'sub_home2', name: 'menu_sub_home' }
];

const selectedItem = ref('home');
const userPreview = ref({ iconUrl: '', name: '', rating: 0, rating_url: null, trophy_title: '', trophy_url: '', course_rank: '', class_rank: '', star: 0, chara: '' });
const ratingData = ref({
  newSongs: [],
  bestSongs: [],
  newCandidates: [],
  bestCandidates: []
});
const isLoading = ref(false);
// 用于存储当前吸顶的screw block元素
const stickyBlock = ref<HTMLElement | null>(null);
// 存储所有screw block的位置信息
const blockOffsets = ref<{[key: string]: number}>({});

const loadRatingData = async () => {
  if (selectedItem.value !== 'rating') return;
  
  isLoading.value = true;
  try {
    const response = await fetch('/api/rating');
    const data = await response.json();
    ratingData.value = data;
  } catch (error) {
    console.error('Failed to fetch rating data:', error);
  } finally {
    isLoading.value = false;
  }
};

const handleItemClick = (itemId: string) => {
  if (itemId === 'home' || itemId === 'rating') {
    selectedItem.value = itemId;
    // 切换标签时重置吸顶状态
    resetStickyBlocks();
    // 重新计算位置
    nextTick(() => {
      calculateBlockOffsets();
    });
    // 加载rating数据
    if (itemId === 'rating') {
      loadRatingData();
    }
  }
};

// 重置所有吸顶块
const resetStickyBlocks = () => {
  const blocks = document.querySelectorAll('.screw_block');
  blocks.forEach(block => {
    block.classList.remove('sticky-active');
  });
  stickyBlock.value = null;
};

// 计算所有screw block的偏移量
const calculateBlockOffsets = () => {
  blockOffsets.value = {};
  const blocks = document.querySelectorAll('.screw_block');
  
  blocks.forEach((block, index) => {
    const rect = block.getBoundingClientRect();
    // 存储相对于视口顶部的偏移量（加上滚动位置）
    blockOffsets.value[`block-${index}`] = window.scrollY + rect.top;
  });
};

// 处理滚动事件
const handleScroll = () => {
  if (selectedItem.value !== 'rating') return;
  
  const scrollY = window.scrollY;
  const blocks = document.querySelectorAll('.screw_block');
  
  // 遍历所有screw block，找到当前应该吸顶的那个
  blocks.forEach((block, index) => {
    const blockKey = `block-${index}`;
    const blockOffset = blockOffsets.value[blockKey];
    
    // 获取下一个block的偏移量
    const nextBlockKey = `block-${index + 1}`;
    const nextBlockOffset = blockOffsets.value[nextBlockKey] || Infinity;
    
    // 判断当前block是否应该吸顶
    if (scrollY >= blockOffset - 10 && scrollY < nextBlockOffset - 10) {
      // 先移除所有block的吸顶状态
      resetStickyBlocks();
      // 给当前block添加吸顶状态
      block.classList.add('sticky-active');
      stickyBlock.value = block as HTMLElement;
    } else if (scrollY < blockOffset - 10 && index === 0) {
      // 滚动到顶部时移除所有吸顶状态
      resetStickyBlocks();
    }
  });
};

onMounted(async () => {
  try {
    const response = await fetch('/api/userpreview');
    const data = await response.json();
    userPreview.value = data;
  } catch (error) {
    console.error('Failed to fetch user preview:', error);
  }
  
  // 计算初始位置
  nextTick(() => {
    calculateBlockOffsets();
    // 添加滚动监听
    window.addEventListener('scroll', handleScroll);
    // 添加窗口大小变化监听
    window.addEventListener('resize', calculateBlockOffsets);
  });
});

onUnmounted(() => {
  // 移除事件监听
  window.removeEventListener('scroll', handleScroll);
  window.removeEventListener('resize', calculateBlockOffsets);
});
</script>

<template>
  <div class="home-sub-menu">
    <div class="sub-menu-container">
      <div
          v-for="(item) in homeSubItems"
          :key="item.id"
          class="sub-menu-item"
          @click="handleItemClick(item.id)"
          :class="{ 'clickable': item.id === 'home' || item.id === 'rating' }"
      >
        <img
            v-if="selectedItem === item.id"
            src="/src/assets/home_sub/icon_on.png"
            alt="icon_on"
            class="sub-menu-icon"
        />
        <img
            :src="`/src/assets/home_sub/${item.name}.png`"
            :alt="item.name"
            class="sub-menu-image"
        />
      </div>
    </div>
  </div>
  <!-- 当选择home时显示的区域 -->
  <div v-if="selectedItem === 'home'">
    <div  class="home-content-wrapper">
      <div class="see_through_block p_10 m_t_10 m_15 t_l">
        <div class="basic_block p_10 f_0">
          <img :src="userPreview.iconUrl" :alt="userPreview.name" class="f_l user_icon" />
          <div class="p_l_10 f_l">
            <div class="trophy_block" v-if="userPreview.trophy_url">
              <img :src="userPreview.trophy_url" alt="trophy" class="trophy_bg" />
              <div class="trophy_inner_block">
                {{ userPreview.trophy_title }}
              </div>
            </div>
            <div class="flex_container user-info-container">
              <div class="name_block f_16">
                {{ userPreview.name }}
              </div>
              <div class="p_r p_3" v-if="userPreview.rating_url">
                <img :src="userPreview.rating_url" alt="rating" />
                <div class="rating_block">
                  {{ userPreview.rating }}
                </div>
              </div>
            </div>
            <img src="/src/assets/line_01.png" alt="line" class="line_image" />
            <div class="rank_images_container">
              <div class="rank_image_wrapper">
                <img :src="userPreview.course_rank" alt="course_rank" class="rank_image" />
              </div>
              <div class="rank_image_wrapper">
                <img :src="userPreview.class_rank" alt="class_rank" class="rank_image" />
              </div>
              <div class="star_container">
                <img src="/src/assets/icon_star.png" alt="star" class="icon_star" />
                <span class="star_text">x{{ userPreview.star }}</span>
              </div>
            </div>
          </div>
        </div>
        <div class="comment_and_chara_block">
          <div class="comment_block">
            <!-- 这里可以添加评论内容 -->
          </div>
          <div class="chara_block">
            <img v-if="userPreview.chara" :src="userPreview.chara" alt="chara" class="chara_image" />
          </div>
        </div>
      </div>
      <div class="option_button_container">
        <img src="/src/assets/home_sub/btn_home_option.png" alt="option" class="option_button" />
      </div>
      <div>
        <div class="container">
          <div class="w_430 f_14 m_t_10">
            <div class="flex_container">
              <!-- 左侧 menu_home 按钮图片 -->
              <div class="menu_home_button">
                <img src="/src/assets/menu/menu_home.png" alt="menu_home" class="menu_home_image" />
              </div>
              <!-- 右侧超文本链接 -->
              <div class="links_container">
                <a href="#" @click.prevent="handleItemClick('home')" class="menu_link" :class="{ 'active': selectedItem === 'home' }">主页</a>
                <a href="#" @click.prevent="handleItemClick('rating')" class="menu_link" :class="{ 'active': selectedItem === 'rating' }">DX评分</a>
              </div>
            </div>
          </div>
          <img src="/src/assets/line_01.png" alt="line" class="line_image" />
        </div>
      </div>
    </div>
    <!-- 动画图片区域 -->
    <footer>
      <div class="footer-content">
        <div class="animation-container">
          <div class="animation-wrapper">
            <img src="/src/assets/back_area_dx.png" alt="back_area_dx" class="animation-image" />
            <img src="/src/assets/back_area_dx.png" alt="back_area_dx" class="animation-image" />
            <img src="/src/assets/back_area_dx.png" alt="back_area_dx" class="animation-image" />
          </div>
        </div>
        <div class="footer-extension">
          <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 480 50" preserveAspectRatio="none" style="height:50px; position: relative; z-index: 1;">
            <path d="M0,0 v25 q5,5 10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 t10,0 v-25 Z" fill="#7cbc29"></path>
          </svg>
        </div>
        <img src="/src/assets/line_01.png" alt="line" class="line_image" />
      </div>
    </footer>
  </div>
  <div v-if="selectedItem === 'rating'">
    <div class="home-content-wrapper2 m_t_10">
      <div class="container p_10 t_l">在这里可以确认乐曲的评分对象曲目
      </div>
      <img src="/src/assets/title_playerdata.png" alt="line" class="line_image m_t_10" />
      <div class="see_through_block p_10 m_t_10 m_15 t_l">
        <div class="basic_block p_10 f_0">
          <img :src="userPreview.iconUrl" :alt="userPreview.name" class="f_l user_icon" />
          <div class="p_l_10 f_l">
            <div class="trophy_block" v-if="userPreview.trophy_url">
              <img :src="userPreview.trophy_url" alt="trophy" class="trophy_bg" />
              <div class="trophy_inner_block">
                {{ userPreview.trophy_title }}
              </div>
            </div>
            <div class="flex_container user-info-container">
              <div class="name_block f_16">
                {{ userPreview.name }}
              </div>
              <div class="p_r p_3" v-if="userPreview.rating_url">
                <img :src="userPreview.rating_url" alt="rating" />
                <div class="rating_block">
                  {{ userPreview.rating }}
                </div>
              </div>
            </div>
            <img src="/src/assets/line_01.png" alt="line" class="line_image" />
            <div class="rank_images_container">
              <div class="rank_image_wrapper">
                <img :src="userPreview.course_rank" alt="course_rank" class="rank_image" />
              </div>
              <div class="rank_image_wrapper">
                <img :src="userPreview.class_rank" alt="class_rank" class="rank_image" />
              </div>
              <div class="star_container">
                <img src="/src/assets/icon_star.png" alt="star" class="icon_star" />
                <span class="star_text">x{{ userPreview.star }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="screw_block m_15 f_15 p_s">评分对象曲目（新曲）</div>
    <div class="p_10" v-if="isLoading">加载中...</div>
    <div class="p_10" v-else>
      <Record_Simple
          v-for="(item, index) in ratingData.newSongs"
          :key="index"
          :level_id="item.level_id"
          :level_String="item.level_String"
          :score="item.score"
          :music_name="item.music_name"
          :dx_score="item.dx_score"
          :comboType="item.comboType"
          :syncType="item.syncType"
          :score-type="item.score_type"
          :type="item.type"

      />
    </div>

    <div class="screw_block m_15 f_15 p_s">评分对象曲目（最佳）</div>
    <div class="p_10" v-if="isLoading">加载中...</div>
    <div class="p_10" v-else>
      <Record_Simple
          v-for="(item, index) in ratingData.bestSongs"
          :key="index"
          :level_id="item.level_id"
          :level_String="item.level_String"
          :score="item.score"
          :music_name="item.music_name"
          :dx_score="item.dx_score"
          :comboType="item.comboType"
          :syncType="item.syncType"
          :score-type="item.score_type"
          :type="item.type"
      />
    </div>

    <div class="screw_block m_15 f_15 p_s">评分候选曲目（新曲）</div>
    <div class="p_10" v-if="isLoading">加载中...</div>
    <div class="p_10" v-else>
      <Record_Simple
          v-for="(item, index) in ratingData.newCandidates"
          :key="index"
          :level_id="item.level_id"
          :level_String="item.level_String"
          :score="item.score"
          :music_name="item.music_name"
          :dx_score="item.dx_score"
          :comboType="item.comboType"
          :syncType="item.syncType"
          :score-type="item.score_type"
          :type="item.type"

      />
    </div>

    <div class="screw_block m_15 f_15 p_s">评分候选曲目（最佳）</div>
    <div class="p_10" v-if="isLoading">加载中...</div>
    <div class="p_10" v-else>
      <Record_Simple
          v-for="(item, index) in ratingData.bestCandidates"
          :key="index"
          :level_id="item.level_id"
          :level_String="item.level_String"
          :score="item.score"
          :music_name="item.music_name"
          :dx_score="item.dx_score"
          :comboType="item.comboType"
          :syncType="item.syncType"
          :score-type="item.score_type"
          :type="item.type"

      />
    </div>
  </div>
</template>

<style>
.p_10 {
  padding: 10px;
}
.t_l {
  text-align: left;
}
.w_410 {
  width: 410px;
}
.p_30 {
  padding: 30px;
}
.screw_block {
  padding: 15px 17px;
  background: url(../../assets/home_sub/icon_screw.png) left top no-repeat, url(../../assets/home_sub/icon_screw.png) right top no-repeat, url(../../assets/home_sub/icon_screw.png) left bottom no-repeat, url(../../assets/home_sub/icon_screw.png) right bottom no-repeat, #fff;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border: 1px solid #b3b3b3;
  border-radius: 5px;
  display: block;
  unicode-bidi: isolate;
  /* 吸顶基础样式 */
  position: relative;
  z-index: 99;
  /* 添加过渡效果 */
  transition: all 0.3s ease;

  position: sticky;
  top: 0;
  z-index: 999;
}

/* 吸顶激活状态 */
.screw_block.sticky-active {
  //position: fixed;
  //top: 0;
  //margin: 0;
  //width: 450px;
  //margin: 0 auto;
  //box-sizing: border-box;
}

.p_s {
  /* 移除原有sticky定位，改用JS控制 */
  /* position: sticky;
  top: 0; */
  z-index: 99;
}
.m_15 {
  margin: 15px;
}
.f_15 {
  font-size: 15px;
  line-height: 1.2rem;
}
.h_500 {
  height: 400px;
}
</style>

<style scoped>
/* 基础样式保持不变，以下是修改和新增的关键样式 */
.home-sub-menu {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.sub-menu-container {
  display: flex;
  align-items: center;
  position: relative;
  width: 100%;
  max-width: 480px;
}

.sub-menu-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  cursor: default;
}

.clickable {
  cursor: pointer;
}

.sub-menu-icon {
  position: absolute;
  top: -10px;
  width: 28px;
  height: 26px;
  z-index: 10;
}

.sub-menu-image {
  width: 100%;
  height: auto;
  max-width: 120px;
  max-height: 42px;
  margin-top: 5px;
}

/* 竖线分隔符 */
.sub-menu-item:not(:last-child)::after {
  content: '';
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 2px;
  height: 20px;
  background-color: rgb(216, 103, 93);
}

/* ========== 关键修复样式 ========== */
/* 新增：主页内容外层容器，限制最大宽度并适配小屏幕 */
.home-content-wrapper {
  width: 100%;
  box-sizing: border-box;
  padding: 0 10px; /* 增加左右内边距，防止贴边 */
  overflow-x: hidden; /* 隐藏横向溢出 */
  position: relative; /* 确保sticky定位正常工作 */
}
.home-content-wrapper2 {
  width: 100%;
  box-sizing: border-box;
  padding: 0 10px; /* 增加左右内边距，防止贴边 */
  overflow-x: hidden; /* 隐藏横向溢出 */
  position: relative; /* 确保sticky定位正常工作 */
}

/* 修复：flex_container 基础样式 */
.flex_container {
  display: flex;
  margin-top: 20px;
  align-items: center;
  width: 100%;
  flex-shrink: 1;
  flex-wrap: wrap;
  box-sizing: border-box; /* 新增：盒模型包含内边距和边框 */
  gap: 8px; /* 新增：子元素之间的间距，替代margin */
}

/* 修复：用户信息容器 - 关键修改 */
.user-info-container {
  max-height: none; /* 移除固定最大高度限制 */
  margin-top: 10px; /* 调整间距 */
  gap: 8px; /* 子元素间距 */
}

/* 修复：名称块 - 适配小屏幕 */
.name_block {
  flex: 1;
  min-width: 0; /* 必须保留，让flex元素可以收缩 */
  height: 33px;
  margin: 0; /* 移除margin，改用gap */
  padding: 5px 3px 3px 3px;
  border: 1px solid #dedede;
  background-color: #fff;
  background-size: 34px;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  min-width: 120px; /* 新增：设置最小宽度，避免过度收缩 */
}

/* 修复：评分容器 - 关键修改 */
.p_r {
  position: relative;
  height: 33px;
  display: flex;
  align-items: center;
  flex-shrink: 1;
  min-width: 0;
  flex: 0 1 auto; /* 新增：只在必要时收缩，不主动扩展 */
  min-width: 80px; /* 新增：设置最小宽度 */
}

.p_3 {
  padding: 3px;
  height: 100%;
  box-sizing: border-box;
  flex-shrink: 1;
  min-width: 0;
}

.p_3 img {
  width: 100%;
  height: auto;
  max-width: 103px;
  flex-shrink: 1;
}

/* 修复：评分文字定位 - 适配小屏幕 */
.rating_block {
  position: absolute;
  top: 50%;
  right: 5px; /* 减小右侧距离 */
  width: auto; /* 移除固定宽度 */
  height: 33px;
  line-height: 21px;
  color: white;
  text-align: right;
  font-size: 14px; /* 小屏幕减小字体 */
  transform: translateY(-36%);
  padding-right: 7px;
  padding-left: 10px; /* 增加左内边距，防止文字溢出 */
}

/* 修复：容器样式 - 适配小屏幕 */
.container {
  display: block;
  width: 100%;
  max-width: 450px;
  height: auto;
  margin: 10px auto; /* 改为水平居中，避免左侧margin */
  position: relative;
  background-color: #fff;
  box-shadow: 0 0 0 2px #2e94f4, 0 0 0 6px #fff, 1px 8px 8px rgba(0, 0, 0, 0.2), 0 12px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  box-sizing: border-box; /* 新增：盒模型包含边框 */
  padding: 0 10px; /* 新增：内边距 */
}

/* 修复：评论和角色区域 - 小屏幕适配 */
.comment_and_chara_block {
  display: flex;
  margin-top: 10px;
  gap: 10px;
}

.comment_block {
  flex: 1;
  min-height: 120px;
  margin: 10px 0 0 0;
  padding: 10px;
  background: #dcf3ff;
  border-radius: 10px;
}

.chara_block {
  margin-top: 10px;
  flex-shrink: 0;
}

@media (max-width: 480px) {
  .sub-menu-container {
    max-width: 100%;
  }

  .sub-menu-item {
    flex: 1;
  }

  .sub-menu-image {
    width: 100%;
    height: auto;
  }

  .sub-menu-item:not(:last-child)::after {
    right: 0;
  }

  .p_l_10 {
    width: 100%;
  }

  .trophy_block {
    max-width: 90%;
    width: 90%;
  }

  /* 重点：小屏幕下依然保持横向排列，不换行！ */
  .user-info-container {
    flex-wrap: nowrap; /* 强制并排，不换行 */
    gap: 5px; /* 缩小间距 */
  }

  /* 小屏幕下调整字体和尺寸，让两个元素能并排放下 */
  .name_block {
    font-size: 12px; /* 缩小字体 */
    height: 28px; /* 降低高度 */
    flex: 2; /* 调整占比，给名称更多空间 */
    min-width: 0; /* 必须保留，允许收缩 */
  }

  .p_r {
    height: 28px; /* 同步降低高度 */
    flex: 1; /* 调整占比，评分区域占1份 */
    min-width: 70px; /* 最小宽度，避免挤太扁 */
    width: auto; /* 恢复自动宽度，不占满整行 */
    margin-top: 0; /* 取消上边距 */
  }

  .p_3 img {
    max-width: 80px; /* 缩小评分背景图 */
  }

  .rating_block {
    font-size: 10px; /* 缩小评分文字 */
    right: 1px;
    transform: translateY(-33%); /* 微调垂直居中 */
  }

  /* 排名区域适配 */
  .rank_images_container {
    flex-wrap: wrap;
    gap: 10px;
  }

  .rank_image_wrapper {
    flex: 1 1 40%; /* 每行显示2个 */
  }

  .star_container {
    flex: 1 1 100%; /* 星星区域占满整行 */
    margin-top: 5px;
  }

  /* 菜单按钮适配 - 保持横向 */
  .flex_container:not(.user-info-container) {
    flex-direction: row; /* 其他flex容器保持横向 */
    align-items: center;
  }

  .menu_home_button {
    margin-left: 0; /* 移除左侧margin */
    margin-right: 10px; /* 右侧加间距 */
    margin-bottom: 0; /* 取消下边距 */
  }

  /* 小屏幕下吸顶样式适配 */
  .screw_block.sticky-active {
    width: calc(100% - 20px);
    padding: 10px 15px;
  }
}
/* ========== 原有样式（保留） ========== */
.see_through_block {
  background-color: rgba(255, 255, 255, 0.6);
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border: 2px solid #000;
  border-radius: 5px;
}

.p_10 {
  padding: 10px;
}

.m_t_10 {
  margin-top: 10px;
}

.m_15 {
  margin: 15px;
}

.t_l {
  text-align: left;
}

.basic_block {
  background-color: #fff;
  box-shadow: 1px 3px 0px rgba(0, 0, 0, 0.4);
  border-radius: 5px;
  overflow: hidden;
  display: flex;
  align-items: flex-start;
}

.f_0 {
  font-size: 0;
}

.f_l {
  /* float: left; */
}

.p_l_10 {
  padding-left: 10px;
  flex: 1;
}

.trophy_block {
  max-width: 100%;
  width: 100%;
  height: 25px;
  margin: 0 0 5px 0;
  white-space: nowrap;
  overflow: hidden;
  position: relative;
}

.trophy_bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}

.trophy_inner_block {
  width: calc(100% - 4px);
  height: 24px;
  margin: 0 2px;
  white-space: nowrap;
  overflow: hidden;
  color: #fff;
  text-shadow: black 1px 1px 0, black -1px -1px 0, black -1px 1px 0, black 1px -1px 0, black 0px 1px 0, black 0 -1px 0, black -1px 0 0, black 1px 0 0;
  position: relative;
  z-index: 2;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
}

.user_name {
  font-size: 16px;
  margin-bottom: 5px;
}

.user_icon {
  width: 112px;
  height: auto;
  object-fit: contain;
  flex-shrink: 0;
}

.m_b_5 {
  margin-bottom: 5px;
}
.margin20 {
  margin-top: 20px;
}

.f_16 {
  font-size: 16px;
}

.line_image {
  width: 100%;
  height: auto;
  margin-top: 10px;
}

.rank_images_container {
  display: flex;
  align-items: center;
  margin-top: 10px;
  width: 100%;
}

.rank_image_wrapper {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 5px;
}

.rank_image {
  height: 30px;
  width: auto;
  max-width: 100%;
  object-fit: contain;
}

.star_container {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 5px;
  white-space: nowrap;
}

.icon_star {
  width: 20px;
  height: 20px;
  margin-right: 5px;
}

.star_text {
  font-size: 16px;
  transform: translateY(-10%);

}

/* 新增图片按钮样式 */
.option_button_container {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}

.option_button {
  width: auto;
  height: auto;
  max-width: 100%;
}

.chara_image {
  width: 120px;
  height: 120px;
  object-fit: contain;
}

.w_430 {
  width: 100%;
  max-width: 430px;
  box-sizing: border-box;
  padding: 15px;
  margin: 0;
}

.f_14 {
  font-size: 14px;
  line-height: 1.2rem;
}

/* 菜单按钮和链接样式 */
.menu_home_button {
  margin-left: 20px;
}

.menu_home_image {
  width: 112px;
  height: 67px;
  object-fit: contain;
}

.links_container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 10px;
}

.menu_link {
  color: #3fc6fc;
}

/* 动画图片样式 */
.animation-container {
  width: 480px;
  height: auto;
  margin: 0 auto;
  overflow: hidden;
  position: relative;
}

.animation-wrapper {
  display: flex;
  width: 300%; /* 3张图片的宽度 */
  height: auto;
  animation: scroll 30s linear infinite;
}

.animation-image {
  width: 33.333%; /* 每张图片占容器1/3宽度 */
  height: auto;
  object-fit: contain;
}

@keyframes scroll {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-33.333%); /* 滚动一个图片宽度 */
  }
}

/* footer样式 */
footer {
  width: 100%;
  margin-top: 10px;
}

.footer-content {
  width: 480px;
  margin: 0 auto;
  position: relative;
}

.footer-content::before {
  content: '';
  position: absolute;
  top: 80%;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(to bottom, #72ae26 0%, #55ab38 100%);
  z-index: -1;
}

.footer-extension {
  width: 480px;
  height: 150px;
  margin: 0 auto;
  background-color: #55ab38;
}
</style>