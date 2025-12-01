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
            @save="onEditVhsSave"
            @cancel="onEditVhsCancel"
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
          @save="onRentVhsSave"
          @cancel="onRentVhsCancel"
        />
      </div>
    </template>
  </Card>
</template>

<script setup>
import { defineProps, ref } from "vue";
import { useConfirm } from "primevue/useconfirm";
import { useToast } from "primevue/usetoast";
import Button from "primevue/button";
import Card from "primevue/card";
import VhsDialog from "@/components/VhsDialog.vue";
import RentCreateDialog from "@/components/RentalCreateDialog.vue";
import { fetchDelete, fetchPost, fetchPut } from "@/services/http";

const props = defineProps({
  vhs: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(["delete", "rent", "edit"]);

const toast = useToast();
const confirm = useConfirm();

const editVisible = ref(false);
const rentCreateVisible = ref(false);

const editVhs = () => (editVisible.value = true);

const onEditVhsCancel = () => (editVisible.value = false);

const onEditVhsSave = async (updatedVhs) => {
  editVisible.value = false;

  const res = await fetchPut(`/vhs/${updatedVhs.id}`, updatedVhs);
  if (res.status != 200) {
    const errorDetail = await res.text();
    showErrorToast(`Can not update ${props.vhs.title}`, errorDetail);
  } else {
    Object.assign(props.vhs, await res.json());

    toast.add({
      severity: "success",
      summary: "VHS updated",
      detail: `${props.vhs.title} has been updated successfully`,
      group: "bl",
      life: 3000,
    });
    emit("edit", props.vhs);
  }
};

const rentVhs = () => (rentCreateVisible.value = true);

const onRentVhsCancel = () => (rentCreateVisible.value = false);

const onRentVhsSave = async (rental) => {
  rentCreateVisible.value = false;
  const res = await fetchPost(`/rentals`, rental);
  if (res.status != 201) {
    const errorDetail = await res.text();
    showErrorToast(`Unable to rent ${props.vhs.title}`, errorDetail);
  } else {
    emit("rent", rental);
  }
};

const deleteVhs = () => {
  confirm.require({
    message: `Do you want to delete ${props.vhs.title}?`,
    header: "Danger Zone",
    icon: "pi pi-info-circle",
    rejectLabel: "Cancel",
    rejectProps: {
      label: "Cancel",
      severity: "secondary",
      outlined: true,
    },
    acceptProps: {
      label: "Delete",
      severity: "danger",
    },
    accept: async () => {
      const res = await fetchDelete(`/vhs/${props.vhs.id}`);
      if (res.status != 204) {
        showErrorToast(
          "Delete fail",
          `Failed to delete VHS ${props.vhs.title}`
        );
      } else {
        toast.add({
          severity: "success",
          summary: "VHS deleted",
          detail: `${props.vhs.title} has been deleted successfully`,
          group: "bl",
          life: 3000,
        });
        emit("delete", props.vhs);
      }
    },
    reject: () => {},
  });
};

const showErrorToast = (summary, detail) =>
  toast.add({
    severity: "error",
    summary,
    detail: detail || "Error",
    group: "bl",
    life: 3000,
  });
</script>

<style scoped>
</style>
