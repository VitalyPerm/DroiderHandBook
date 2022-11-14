package com.elvitalya.droiderhandbook.ui.sections

import androidx.lifecycle.ViewModel
import com.elvitalya.droiderhandbook.utils.FireBaseHelper
import kotlinx.coroutines.flow.MutableStateFlow

class SectionsViewModel : ViewModel() {

    val sectionsList = MutableStateFlow<List<String>>(emptyList())

    fun getSections() {
        FireBaseHelper.mainSection
            .get()
            .addOnSuccessListener { snapShot ->
                sectionsList.value = snapShot.documents.map { documentSnapshot ->
                    documentSnapshot.data?.values?.first().toString()
                }

            }
    }
}