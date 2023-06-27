package com.app.wetravel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ErrorBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): ErrorBottomSheetDialog {
            return ErrorBottomSheetDialog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_error_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 设置点击外部区域是否关闭弹窗
        isCancelable = true
        // 在这里可以对弹窗中的视图进行操作和设置，例如显示错误信息等
    }
}
