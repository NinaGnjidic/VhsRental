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
      <Button icon="pi pi-search" label="Search" @click="loadVhs" />
    </div>
    <Button icon="pi pi-plus" variant="outlined" label="Add VHS" @click="createVhs" />
    <VhsDialog
      v-model:visible="createVisible"
      :vhs="{ title: '', genre: '', releaseYear: new Date().getFullYear() }"
      dialogTitle="Create VHS"
      dialogDescription="Please insert the VHS information below."
      @save="onVhsCreated"
      @cancel="loadVhs"
    />
  </div>
  <div class="flex flex-wrap gap-4">
    <VhsCard
      v-for="vhs in vhsList"
      :vhs="vhs"
      :key="vhs.id"
      @delete="onVhsEdited"
      @edit="onVhsEdited"
      @rent="onVhsRented"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useToast } from 'primevue/usetoast'
import Button from 'primevue/button'
import Dropdown from 'primevue/dropdown'
import VhsDialog from '@/components/VhsDialog.vue'
import VhsCard from '@/components/VhsCard.vue'
import { fetchGet, fetchPost } from '@/services/http'

const emit = defineEmits(['rentalsChange'])

const vhsList = ref([])
const genres = ref([])
const releaseYears = ref([])
const selectedGenre = ref(null)
const selectedReleaseYear = ref(null)

const toast = useToast()

onMounted(async () => await loadData())

const loadData = async () => {
  vhsList.value = await getVhs()
  genres.value = await getGenres()
  releaseYears.value = await getReleaseYears()
}

const getGenres = async () => {
  try {
    const res = await fetchGet('/vhs/genres')
    if (res.status != 200) {
      const errorDetail = await res.text()
      showErrorToast('Can not get genres', errorDetail)
    }
    return res.json()
  } catch (err) {
    showErrorToast('Can not get genres', err)
  }
}

const getReleaseYears = async () => {
  try {
    const res = await fetchGet('/vhs/releaseYears')
    if (res.status != 200) {
      const errorDetail = await res.text()
      showErrorToast('Can not get release years', errorDetail)
    }
    return res.json()
  } catch (err) {
    showErrorToast('Can not get release years', err)
  }
}

const loadVhs = async () => (vhsList.value = await getVhs())

const getVhs = async () => {
  try {
    const params = new URLSearchParams()
    if (selectedGenre.value) params.append('genre', selectedGenre.value)
    if (selectedReleaseYear.value) params.append('releaseYear', selectedReleaseYear.value)

    const res = await fetchGet(`/vhs?${params.toString()}`)
    if (res.status != 200) {
      const errorDetail = await res.text()
      showErrorToast('Failed to fetch VHS data', errorDetail)
    }
    return res.json()
  } catch (err) {
    showErrorToast('Failed to fetch VHS data', err)
  }
}

const createVisible = ref(false)

const createVhs = () => (createVisible.value = true)

const onVhsCreated = async (createdVhs) => {
  createVisible.value = false

  const res = await fetchPost('/vhs', createdVhs)
  if (res.status != 201) {
    const errorDetail = await res.text()
    showErrorToast('Failed to create VHS', errorDetail)
  } else {
    toast.add({
      severity: 'success',
      summary: 'VHS created',
      detail: `${createdVhs.title} has been created successfully`,
      group: 'bl',
      life: 3000,
    })
  }

  await loadData()
}

const onVhsRented = (rental) => emit('rentalsChange', rental)

const onVhsEdited = async (rental) => {
  await loadData()
  emit('rentalsChange', rental)
}

const showErrorToast = (summary, detail) =>
  toast.add({
    severity: 'error',
    summary,
    detail: detail || 'Error',
    group: 'bl',
    life: 3000,
  })
</script>