<template>
  <div>
    <h2>VHS Rental Management System</h2>
  </div>
  <Tabs value="0">
    <TabList>
      <Tab value="0">VHS</Tab>
      <Tab value="1">Rental</Tab>
    </TabList>
    <TabPanels>
      <TabPanel value="0">
        <VhsCardList @rentalsChange="onRentalsChange" />
      </TabPanel>
      <TabPanel value="1">
        <RentalList ref="rentalListRef" />
      </TabPanel>
    </TabPanels>
  </Tabs>
  <Toast position="bottom-left" group="bl" />
  <ConfirmDialog />
</template>

<script setup>
import { ref } from 'vue'

import Tabs from 'primevue/tabs'
import TabList from 'primevue/tablist'
import Tab from 'primevue/tab'
import TabPanels from 'primevue/tabpanels'
import TabPanel from 'primevue/tabpanel'
import VhsCardList from '@/components/VhsCardList.vue'
import RentalList from '@/components/RentalList.vue'
import ConfirmDialog from 'primevue/confirmdialog'
import Toast from 'primevue/toast'

const rentalListRef = ref(null)

const onRentalsChange = () => rentalListRef.value?.loadRentals()
</script>