<template>
  <Dialog v-model:visible="visible" modal :header="dialogTitle" :style="{ width: '25rem' }">
    <span class="block mb-4">{{ dialogDescription }}</span>
    <div class="flex items-center gap-4 mb-4">
      <label for="title" class="font-semibold w-24">Title</label>
      <InputText id="title" class="flex-auto" autocomplete="off" v-model="internalVhs.title" />
    </div>
    <div class="flex items-center gap-4 mb-4">
      <label for="genre" class="font-semibold w-24">Genre</label>
      <InputText id="genre" class="flex-auto" autocomplete="off" v-model="internalVhs.genre" />
    </div>
    <div class="flex items-center gap-4 mb-8">
      <label for="releaseYear" class="font-semibold w-24">Release Year</label>
      <DatePicker id="releaseYear" v-model="releaseYearDate" view="year" dateFormat="yy" showIcon />
    </div>
    <div class="flex justify-end gap-2">
      <Button type="button" label="Cancel" severity="secondary" @click="cancelDialog" />
      <Button type="button" label="Save" @click="saveDialog" />
    </div>
  </Dialog>
</template>

<script setup>
import { ref, watch, reactive } from 'vue'
import Dialog from 'primevue/dialog'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import DatePicker from 'primevue/calendar'

const props = defineProps({
  modelValue: Boolean,
  vhs: { type: Object, required: true },
  dialogTitle: { type: String, default: '' },
  dialogDescription: { type: String, default: '' },
})

const emit = defineEmits(['update:modelValue', 'save', 'cancel'])

const visible = ref(props.modelValue)
watch(
  () => props.modelValue,
  (val) => (visible.value = val)
)
watch(visible, (val) => emit('update:modelValue', val))

const internalVhs = reactive({
  id: props.vhs.id || '',
  title: props.vhs.title || '',
  genre: props.vhs.genre || '',
  releaseYear: props.vhs.releaseYear || new Date().getFullYear(),
})

const releaseYearDate = ref(new Date((props.vhs.releaseYear || new Date().getFullYear()), 0, 1))

watch(visible, (val) => {
  if (val) {
    internalVhs.id = props.vhs.id || ''
    internalVhs.title = props.vhs.title || ''
    internalVhs.genre = props.vhs.genre || ''
    internalVhs.releaseYear = props.vhs.releaseYear || new Date().getFullYear()
    releaseYearDate.value = new Date(internalVhs.releaseYear, 0, 1)
  }
})

watch(releaseYearDate, (val) => {
  if (val instanceof Date && !isNaN(val)) {
    internalVhs.releaseYear = val.getFullYear()
  }
})

const cancelDialog = () => {
  visible.value = false
  emit('cancel')
}

const saveDialog = () => {
  visible.value = false
  emit('save', { ...internalVhs })
}
</script>
