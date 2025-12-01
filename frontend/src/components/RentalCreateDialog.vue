<template>
  <Dialog v-model:visible="visible" modal :header="'Rent ' + vhs.title" :style="{ width: '25rem' }">
    <span class="block mb-4">Select user to rent {{ vhs.title }}</span>
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
import { useToast } from 'primevue/usetoast'
import { fetchGet } from '@/services/http'

const props = defineProps({
  modelValue: Boolean,
  vhs: { type: Object, required: true },
})

const emit = defineEmits(['update:modelValue', 'save', 'cancel'])

const users = ref([])
const selectedUser = ref()
const filteredUsers = ref([])

onMounted(async () => await loadUsers())

const loadUsers = async () => {
  try {
    const res = await fetchGet('/users')
    if (res.status != 200) {
      const errorDetail = await res.text()
      showErrorToast('Can not get users', errorDetail)
    } else {
      users.value = await res.json()
    }
  } catch (err) {
    showErrorToast('Can not get users', err)
  }
}

const toast = useToast()

const showErrorToast = (summary, detail) =>
  toast.add({
    severity: 'error',
    summary,
    detail: detail || 'Error',
    group: 'bl',
    life: 3000,
  })

const searchUsers = (event) => {
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

const visible = ref(props.modelValue)
watch(
  () => props.modelValue,
  (val) => (visible.value = val)
)
watch(visible, (val) => emit('update:modelValue', val))

const cancelDialog = () => {
  visible.value = false
  emit('cancel')
}

const saveDialog = () => {
  visible.value = false
  emit('save', { vhsId: props.vhs.id, userId: selectedUser.value.id })
}
</script>
