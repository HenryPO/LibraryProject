package com.mobile.library.utils.system;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mobile.library.utils.Utils;

/**
 * 软键盘设置
 */
public class KeyBoardUtil {
	private KeyBoardUtil() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}


	/**
	 * 动态隐藏软键盘
	 *
	 * @param activity
	 *            activity
	 */
	public static void hideSoftInput(Activity activity) {
		View view = activity.getCurrentFocus();
		if (view == null)
			view = new View(activity);
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}


	/**
	 * 动态显示软键盘
	 *
	 * @param edit
	 *            输入框
	 */
	public static void showSoftInput(EditText edit) {
		edit.setFocusable(true);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();
		InputMethodManager imm = (InputMethodManager) Utils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(edit, 0);
	}

	/**
	 * 切换键盘显示与否状态
	 */
	public static void toggleSoftInput() {
		InputMethodManager imm = (InputMethodManager) Utils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	/**
	 * 关闭软键盘
	 * @param activity 当前环境
	 * @param mEditText 输入框
     */
	public static void setKeyBoardInvisible(Activity activity, EditText mEditText) {
		// 关闭软键盘
		// 获取负责系统输入的InputMethodManager
		InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 关闭软键盘，第一个参数为EditText的token，第二个参数表示一般性的隐藏
		inputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 显示软键盘
	 * @param activity 当前环境
	 * @param mEditText 输入框
     */
	public static void setKeyBoardVisible(Activity activity, EditText mEditText) {
		// 显示软键盘
		// 获取负责系统输入的InputMethodManager
		InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 打开软键盘，第一个参数为输入焦点的位置，一般为某EditText，第二个参数表示显式地调用
		inputManager.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);

	}

}
