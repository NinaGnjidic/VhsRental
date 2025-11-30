<template>
  <DataTable :value="rentals" paginator :rows="10" responsiveLayout="scroll" :rowClass="rowClass">
    <!-- VHS Title -->
    <Column field="vhs.title" header="VHS" sortable></Column>

    <!-- Genre -->
    <Column field="vhs.genre" header="Genre" sortable></Column>

    <!-- User -->
    <Column field="user.name" header="Rented By" sortable></Column>

    <!-- Rental Date -->
    <Column
      field="rentalDate"
      header="Rented On"
      :body="formatDate('rentalDate')"
      sortable
    ></Column>

    <!-- Due Date -->
    <Column field="dueDate" header="Due By" :body="formatDate('dueDate')" sortable></Column>

    <!-- Return Date -->
    <Column
      field="returnDate"
      header="Returned On"
      :body="formatDate('returnDate')"
      sortable
    ></Column>

    <!-- Late Fee -->
    <Column field="lateFee" header="Late Fee" :body="formatFee"></Column>

    <!-- Status -->
    <Column header="Status">
      <template #body="{ data }">
        <span>
          {{
            data.returnDate
              ? 'Returned'
              : new Date(data.dueDate) < new Date()
              ? 'Overdue'
              : 'Active'
          }}
        </span>
      </template>
    </Column>

    <!-- Actions -->
    <Column header="Actions" style="min-width: 150px">
      <template #body="{ data }">
        <div class="flex gap-2">
          <Button
            v-if="!data.returnDate"
            label="Return"
            icon="pi pi-check"
            severity="success"
            rounded
            size="small"
            @click="markReturned(data)"
          />
          <Button
            label="Delete"
            icon="pi pi-trash"
            severity="danger"
            rounded
            size="small"
            @click="deleteRental(data)"
          />
        </div>
      </template>
    </Column>
  </DataTable>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import { fetchGet, fetchPatch, fetchDelete } from '@/services/http'
import { useToast } from 'primevue/usetoast'

const toast = useToast()
const rentals = ref([])

// Fetch rentals from API
async function loadRentals() {
  try {
    const res = await fetchGet('/rentals')
    if (!res.ok) throw new Error('Failed to fetch rentals')
    rentals.value = await res.json()
  } catch (err) {
    toast.add({
      severity: 'error',
      summary: 'Error loading rentals',
      detail: err.message,
      life: 3000,
    })
  }
}

onMounted(loadRentals)

// Format date helper
const formatDate = (field) => (rowData) =>
  rowData[field] ? new Date(rowData[field]).toLocaleDateString() : '-'

// Format lateFee
function formatFee(rowData) {
  return rowData.lateFee ? `$${rowData.lateFee.toFixed(2)}` : '$0.00'
}

// Row highlight for overdue
function rowClass(rowData) {
  if (!rowData.returnDate && new Date(rowData.dueDate) < new Date()) {
    return 'overdue-row'
  }
  return ''
}

// Mark rental as returned
async function markReturned(rental) {
  try {
    const res = await fetchPatch(`/rentals/${rental.id}`, { returnDate: formatDate2(new Date()) })
    if (!res.ok) throw new Error('Failed to mark returned')
    rental.returnDate = formatDate2(new Date())
    toast.add({
      severity: 'success',
      summary: 'Rental returned',
      detail: `${rental.vhs.title} marked as returned`,
      life: 3000,
    })
  } catch (err) {
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: err.message,
      life: 3000,
    })
  }
}

// Delete rental
async function deleteRental(rental) {
  try {
    const res = await fetchDelete(`/rentals/${rental.id}`)
    if (res.status != 204) throw new Error('Failed to delete rental')
    rentals.value = rentals.value.filter((r) => r.id !== rental.id)
    toast.add({
      severity: 'info',
      summary: 'Rental deleted',
      detail: `${rental.vhs.title} deleted`,
      life: 3000,
    })
  } catch (err) {
    const errorMessage = await res.text()
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: errorMessage,
      life: 3000,
    })
  }
}

function formatDate2(d) {
  const year = d.getFullYear()
  const month = String(d.getMonth() + 1).padStart(2, '0') // months start at 0
  const day = String(d.getDate()).padStart(2, '0')

  return `${year}-${month}-${day}`
}
defineExpose({ loadRentals })
</script>

<style scoped>
.overdue-row {
  background-color: #ffe6e6 !important; /* light red for overdue rentals */
}
</style>
