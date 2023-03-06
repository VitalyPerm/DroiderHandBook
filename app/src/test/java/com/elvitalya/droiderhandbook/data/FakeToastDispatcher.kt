package com.elvitalya.droiderhandbook.data

import com.elvitalya.domain.toastdispatcher.ToastDispatcher

class FakeToastDispatcher : ToastDispatcher {

    var message = ""

    override fun dispatchUnique(value: String?) {
        message = value ?: return
    }
}