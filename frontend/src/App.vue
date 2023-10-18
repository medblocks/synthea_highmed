<script setup>
import {RouterView } from 'vue-router'
import { onMounted, ref } from 'vue'
import AppLoader from './components/AppLoader.vue'
import { useMainStore } from './stores/mainStore'

const mainStore = useMainStore()

const appLoading = ref(true)
onMounted(() => {
  mainStore
    .init()
    .then(() => {
      appLoading.value = false
    })
    .catch((error) => {
      console.error(error)
    })
})
</script>

<template>
  <RouterView v-if="!appLoading" />
  <AppLoader v-else />
</template>
