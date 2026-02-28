<script setup lang="ts">
import { ref } from 'vue';
import Index from './components/Index.vue';
import HomeSubMenu from './components/HomeSubMenu.vue';

const currentMenu = ref('home');

const handleMenuChange = (menuId: string) => {
  currentMenu.value = menuId;
};
</script>

<template>
  <div class="index-container">
    <!-- 背景图片 -->
    <img src="./assets/back_base.png" alt="Background" class="background-image" />
    
    <div class="header-container">
      <div class="logo-wrapper">
        <img src="./assets/logo.png" alt="Logo" class="logo" />
      </div>
      <img src="./assets/btn_setting.png" alt="Setting" class="setting-btn" />
    </div>
    <Index @menu-change="handleMenuChange" />
    
    <!-- Home 子菜单 -->
    <HomeSubMenu v-if="currentMenu === 'home'" />
  </div>
</template>

<style scoped>
.logo {
  width: 260px;
  padding: 5px;
  box-sizing: border-box;
}

.header-container {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.logo-wrapper {
  display: flex;
  justify-content: center;
  width: 100%;
}

.setting-btn {
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-10%);
  width: 34px;
  height: auto;
  cursor: pointer;
  z-index: 10;
}

.index-container {
  min-height: 100vh;
  position: relative;
  overflow: hidden;
}

.background-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: auto;
  object-fit: contain;
  z-index: -1;
  transform: translateY(+14%);
}
</style>
