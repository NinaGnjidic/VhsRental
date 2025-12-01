<template>
  <DataTable :value="rentals" paginator :rows="10" responsiveLayout="scroll">
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
import { useToast } from 'primevue/usetoast'
import { fetchGet, fetchPatch, fetchDelete } from '@/services/http'

const rentals = ref([])

const toast = useToast()

onMounted(async () => await loadRentals())

const loadRentals = async () => {
  try {
    const res = await fetchGet('/rentals')
    if (res.status != 200) {
      const errorDetail = await res.text()
      showErrorToast('Can not get rentals', errorDetail)
    } else {
      rentals.value = await res.json()
    }
  } catch (err) {
    showErrorToast('Can not get rentals', err)
  }
}

const formatDate = (field) => (rowData) => rowData[field] ? new Date(rowData[field]).toLocaleDateString() : '-'

const formatFee = (rowData) => (rowData.lateFee ? `$${rowData.lateFee.toFixed(2)}` : '$0.00')

const markReturned = async (rental) => {
  try {
    const now = new Date().toISOString().slice(0, 10)

    const res = await fetchPatch(`/rentals/${rental.id}`, { returnDate: now })
    if (res.status != 200) {
      const errorDetail = await res.text()
      showErrorToast(`Failed to mark returned ${rental.vhs.title}`, errorDetail)
    } else {
      rental.returnDate = now

      toast.add({
        severity: 'success',
        summary: 'Rental returned',
        detail: `${rental.vhs.title} marked as returned`,
        group: 'bl',
        life: 3000,
      })
    }
  } catch (err) {
    showErrorToast(`Failed to mark returned ${rental.vhs.title}`, err)
  }
}

const deleteRental = async (rental) => {
  try {
    const res = await fetchDelete(`/rentals/${rental.id}`)
    if (res.status != 204) {
      showErrorToast('Delete fail', `Failed to delete rental of ${rental.vhs.title}`)
    } else {
      await loadRentals()

      toast.add({
        severity: 'success',
        summary: 'Rental deleted',
        detail: `${rental.vhs.title} deleted`,
        group: 'bl',
        life: 3000,
      })
    }
  } catch (err) {
    showErrorToast('Delete fail', err)
  }
}

const showErrorToast = (summary, detail) =>
  toast.add({
    severity: 'error',
    summary,
    detail: detail || 'Error',
    group: 'bl',
    life: 3000,
  })

defineExpose({ loadRentals })
</script>