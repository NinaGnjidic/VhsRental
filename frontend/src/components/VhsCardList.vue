<template>
  <div class="flex justify-content-between pb-4">
    <div class="flex flex-wrap gap-2">
      <Dropdown v-model="selectedGenre" :options="genres" placeholder="Select Genre" showClear />
      <Dropdown
        v-model="selectedReleaseYear"
        :options="releaseYears"
        placeholder="Select Release Year"
        showClear
      />
      <Button icon="pi pi-search" label="Search" @click="searchVhs" />
    </div>
    <Button icon="pi pi-plus" variant="outlined" label="Add VHS" @click="createVhs" />
    <VhsDialog
      v-model:visible="createVisible"
      :vhs="{ title: '', genre: '', releaseYear: new Date().getFullYear() }"
      dialogTitle="Create VHS"
      dialogDescription="Please insert the VHS information below."
      @save="onVhsCreated"
      @cancel="onVhsCreateCancel"
    />
  </div>
  <div class="flex flex-wrap gap-4">
    <VhsCard
      v-for="vhs in vhsList"
      :vhs="vhs"
      :key="vhs.id"
      @delete="onVhsDeleted"
      @rent="onVhsRented"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { fetchGet, fetchPost } from '@/services/http'
import Button from 'primevue/button'

import Dropdown from 'primevue/dropdown'

import VhsDialog from '@/components/VhsDialog.vue'

import VhsCard from '@/components/VhsCard.vue'
const emit = defineEmits(['rent'])

const vhsList = ref([])
const genres = ref([])
const releaseYears = ref([])
const selectedGenre = ref(null)
const selectedReleaseYear = ref(null)
const createVisible = ref(false)

// fetch data when component mounts
onMounted(async () => {
  await initData()
})

async function initData() {
  vhsList.value = await getVhs()
  genres.value = await getGenres()
  releaseYears.value = await getReleaseYears()
}

async function getGenres() {
  try {
    const res = await fetchGet('/vhs/genres')
    if (!res.ok) throw new Error('Failed to fetch VHS genres')
    return res.json()
  } catch (err) {
    console.error(err)
  }
}

async function getReleaseYears() {
  try {
    const res = await fetchGet('/vhs/releaseYears')
    if (!res.ok) throw new Error('Failed to fetch VHS release years')
    return res.json()
  } catch (err) {
    console.error(err)
  }
}

async function searchVhs() {
  vhsList.value = await getVhs()
}

async function getVhs() {
  try {
    const params = new URLSearchParams()
    if (selectedGenre.value) params.append('genre', selectedGenre.value)
    if (selectedReleaseYear.value) params.append('releaseYear', selectedReleaseYear.value)

    const res = await fetchGet(`/vhs?${params.toString()}`)
    if (!res.ok) throw new Error('Failed to fetch VHS data')
    return res.json()
  } catch (err) {
    console.error(err)
  }
}

function createVhs() {
  createVisible.value = true
}

async function onVhsCreated(createdVhs) {
  createVisible.value = false
  console.log(createdVhs)

  const res = await fetchPost('/vhs', createdVhs)
  if (!res.ok) throw new Error('Failed to create VHS')

  await initData()
}

async function onVhsCreateCancel(vhs) {
  vhsList.value = await getVhs()
}

async function onVhsDeleted(vhsDeleted) {
  await initData()
}

async function onVhsRented(rental) {
  console.log(rental)
  emit('rent', rental)
}
</script>

<style>
/* optional styling */
.flex-wrap {
  display: flex;
  flex-wrap: wrap;
}
</style>
