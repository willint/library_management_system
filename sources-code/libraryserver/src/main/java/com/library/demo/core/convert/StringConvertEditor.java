package com.library.demo.core.convert;

import java.beans.PropertyEditorSupport;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * 字符串处理类 String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
 */
public class StringConvertEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) {
		setValue(text == null ? null : StringEscapeUtils.escapeHtml(text.trim()));
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return value != null ? value.toString() : "";
	}
}
