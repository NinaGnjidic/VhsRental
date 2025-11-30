<template>
  <Card style="width: 25rem; overflow: hidden">
    <template #header>
      <img alt="movie header" src="/default.png" />
    </template>
    <template #title>
      <div class="flex justify-content-between">
        <div>{{ vhs.title }}</div>
        <div>
          <Button
            icon="pi pi-pencil"
            severity="warn"
            rounded
            variant="outlined"
            aria-label="Edit"
            @click="editVhs"
          />
          <VhsDialog
            v-model:visible="editVisible"
            :vhs="vhs"
            dialogTitle="Edit VHS"
            dialogDescription="Please update the VHS information below."
            @save="onVhsEditSaved"
            @cancel="onVhsEditCanceled"
          />
          <Button
            icon="pi pi-trash"
            severity="danger"
            rounded
            variant="outlined"
            aria-label="Delete"
            @click="deleteVhs"
          />
        </div>
      </div>
    </template>
    <template #subtitle> {{ vhs.genre }} | {{ vhs.releaseYear }} </template>
    <template #footer>
      <div class="flex justify-content-end mt-1">
        <Button label="Rent" class="w-6" @click="rentVhs" />
        <RentCreateDialog
          v-model:visible="rentCreateVisible"
          :vhs="vhs"
          @save="onRentCreateSave"
          @cancel="onRentCreateCancel"
        />
      </div>
    </template>
  </Card>
</template>

<script setup>
import { defineProps, ref } from 'vue'
import Button from 'primevue/button'
import Card from 'primevue/card'
import VhsDialog from '@/components/VhsDialog.vue'
import RentCreateDialog from '@/components/RentalCreateDialog.vue'
import { fetchDelete, fetchPost, fetchPut } from '@/services/http'
import { useConfirm } from 'primevue/useconfirm'

const emit = defineEmits(['delete', 'rent'])

import { useToast } from 'primevue/usetoast'

const toast = useToast()

const props = defineProps({
  vhs: {
    type: Object,
    required: true,
  },
})

const editVisible = ref(false)
const rentCreateVisible = ref(false)

function editVhs() {
  editVisible.value = true
}

function onVhsEditCanceled(vhs) {
  editVisible.value = false
}

async function onVhsEditSaved(updatedVhs) {
  editVisible.value = false
  const res = await fetchPut(`/vhs/${updatedVhs.id}`, updatedVhs)
  if (!res.ok) throw new Error('Failed to put VHS data')
  Object.assign(props.vhs, await res.json())
}

function rentVhs() {
  console.log(props.vhs)
  rentCreateVisible.value = true
}

async function onRentCreateSave(rental) {
  console.log(rental)
  rentCreateVisible.value = false
  const res = await fetchPost(`/rentals`, rental)
  if (res.status != 201) {
    let createError = await res.text()
    toast.add({
      severity: 'warn',
      summary: 'Unable to rent',
      detail: createError,
      group: 'bl',
      life: 3000,
    })
  } else {
    emit('rent', rental)
  }
}
async function onRentCreateCancel() {
  rentCreateVisible.value = false
}

const confirm = useConfirm()
const deleteVhs = () => {
  confirm.require({
    message: `Do you want to delete ${props.vhs.title}?`,
    header: 'Danger Zone',
    icon: 'pi pi-info-circle',
    rejectLabel: 'Cancel',
    rejectProps: {
      label: 'Cancel',
      severity: 'secondary',
      outlined: true,
    },
    acceptProps: {
      label: 'Delete',
      severity: 'danger',
    },
    accept: async () => {
      const res = await fetchDelete(`/vhs/${props.vhs.id}`)
      emit('delete', props.vhs)
    },
    reject: () => {},
  })
}
</script>

<style scoped>
</style>
