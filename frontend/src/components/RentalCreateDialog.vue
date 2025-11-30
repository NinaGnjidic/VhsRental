<template>
  <Dialog v-model:visible="visible" modal :header="'Rent ' + vhs.title" :style="{ width: '25rem' }">
    <span class="text-surface-500 block mb-4">Select user to rent {{ vhs.title }}</span>

    <div class="flex items-center gap-4 mb-4">
      <label for="user" class="font-semibold w-24">User</label>
      <AutoComplete
        id="user"
        v-model="selectedUser"
        optionLabel="name"
        :suggestions="filteredUsers"
        @complete="searchUsers"
        dropdown
      />
    </div>

    <div class="flex justify-end gap-2">
      <Button type="button" label="Cancel" severity="secondary" @click="cancelDialog" />
      <Button type="button" label="Save" @click="saveDialog" />
    </div>
  </Dialog>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import Dialog from 'primevue/dialog'
import Button from 'primevue/button'
import AutoComplete from 'primevue/autocomplete'
import { fetchGet } from '@/services/http'

const props = defineProps({
  modelValue: Boolean,
  vhs: { type: Object, required: true },
})

const emit = defineEmits(['update:modelValue', 'save', 'cancel'])

const visible = ref(props.modelValue)
watch(
  () => props.modelValue,
  (val) => (visible.value = val)
)
watch(visible, (val) => emit('update:modelValue', val))

const users = ref([])
const selectedUser = ref()
const filteredUsers = ref([])

onMounted(async () => {
  try {
    const res = await fetchGet('/users') // adjust endpoint
    if (!res.ok) throw new Error('Failed to fetch users')
    users.value = await res.json()
  } catch (err) {
    console.error(err)
  }
})

function searchUsers(event) {
  setTimeout(() => {
    if (!event.query.trim().length) {
      filteredUsers.value = [...users.value]
    } else {
      filteredUsers.value = users.value.filter((user) => {
        return user.name.toLowerCase().startsWith(event.query.toLowerCase())
      })
    }
  }, 250)
}

function cancelDialog() {
  visible.value = false
  emit('cancel')
}

function saveDialog() {
  visible.value = false
  emit('save', { vhsId: props.vhs.id, userId: selectedUser.value.id })
}
</script>
