/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.common.exception;

public class HzDBException extends RuntimeException {

	private static final long serialVersionUID = -6800034415946664295L;

	public HzDBException(String message, Throwable cause) {
		super(message, cause);
	}

	public HzDBException(String message) {
		super(message);
	}
}
