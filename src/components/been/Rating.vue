<script setup lang="ts">
defineProps<{
  rating: number;
}>();

const getRatingImage = (rating: number): string => {
  if (rating >= 15000) return '/src/assets/rating/UI_CMN_DXRating_11.png';
  if (rating >= 14500) return '/src/assets/rating/UI_CMN_DXRating_10.png';
  if (rating >= 14000) return '/src/assets/rating/UI_CMN_DXRating_9.png';
  if (rating >= 13000) return '/src/assets/rating/UI_CMN_DXRating_8.png';
  if (rating >= 12000) return '/src/assets/rating/UI_CMN_DXRating_7.png';
  if (rating >= 11000) return '/src/assets/rating/UI_CMN_DXRating_6.png';
  if (rating >= 10000) return '/src/assets/rating/UI_CMN_DXRating_5.png';
  if (rating >= 9000) return '/src/assets/rating/UI_CMN_DXRating_4.png';
  if (rating >= 6000) return '/src/assets/rating/UI_CMN_DXRating_3.png';
  if (rating >= 4000) return '/src/assets/rating/UI_CMN_DXRating_2.png';
  if (rating >= 2000) return '/src/assets/rating/UI_CMN_DXRating_1.png';
  return '/src/assets/rating/UI_CMN_DXRating_0.png';
};
</script>

<template>
  <div class="p_r p_3">
    <img :src="getRatingImage(rating)" alt="rating" />
    <div class="rating_block">
      {{ rating }}
    </div>
  </div>
</template>

<style scoped>
.p_r {
  position: relative;
  height: 40px;
  display: flex;
  align-items: center;
  flex-shrink: 1;
  min-width: 0;
  flex: 0 1 auto;
  min-width: 70px;
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
  max-width: 120px;
  flex-shrink: 1;
}

.rating_block {
  position: absolute;
  top: 50%;
  right: 10px;
  width: auto;
  height: 50px;
  line-height: 25px;
  color: white;
  text-align: right;
  font-size: 14px;
  transform: translateY(-26%);
  padding-right: 10px;
  padding-left: 12px;
}
</style>
