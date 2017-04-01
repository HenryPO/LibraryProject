package com.mobile.library.utils;

import java.util.Date;
import java.util.Hashtable;
import java.util.Random;

public class IdGeneratorUtil {
	private static IdGeneratorUtil _this = new IdGeneratorUtil();

	public static IdGeneratorUtil Instance() {
		return _this;
	}

	// ��������Id��ʱ��̶�
	private static final long timescale = 100;

	// ����Id�����������
	private static final int randomLength = 3;

	// ����ʱ�����Ŀ�ʼʱ��
	private static final Date startDateTime = new Date(2012 - 1990, 1 - 1, 1);

	// / <summary>
	// / ���������
	// / </summary>
	private static Hashtable ht = new Hashtable();

	// / <summary>
	// / ʱ������棨��һ�μ���ID��ϵͳʱ�䰴ʱ����̶�ȡֵ��
	// / </summary>
	private long lastEndDateTimeTicks;

	// / <summary>
	// / ��ȡ��һ��long���͵�Id
	// / </summary>
	// / <returns>
	// / ����������һ��Id
	// / </returns>
	public long NextLong() throws Exception {
		// ȡ��ʱ�������ǰʱ�䰴�̶�ȡֵ��
		long timestamp = GetTimestamp(startDateTime, timescale);

		// ��һ��ʱ������º���»���
		if (timestamp != lastEndDateTimeTicks)
			ht.clear();

		// ��
		long power = pow(10, randomLength);
		// �����
		long rand = GetRandom(randomLength);
		// ���ɽ����Id��
		long result = timestamp * power + rand;

		// ��������ظ�
		if (ht.containsKey(result)) {
			boolean resultIsRepeated = true;
			// ����������ȷ�Χ�����ظ�����һ��
			for (int i = 0; i < power; i++) {
				rand = GetRandom(randomLength);
				result = timestamp * power + rand;
				// ���ַ��ظ���Id
				if (!ht.containsKey(result)) {
					// ���µ�Id����HashTable����
					ht.put(result, result);
					resultIsRepeated = false;
					break;// �ҵ�һ��ͬһʱ����ڵ�Id���˳�
				}
			}
			if (resultIsRepeated)
				throw new Exception("���ɵ�Id�ظ�");
		} else {
			// ���µ�Id����HashTable����
			ht.put(result, result);
		}
		// ��¼��ǰһ��ʱ�������ǰʱ�䰴�̶�ȡֵ��
		this.lastEndDateTimeTicks = timestamp;

		return result;
	}

	// / <summary>
	// / ����ʱ����̶ȼ��㵱ǰʱ���
	// / </summary>
	// / <param name="startDateTime">��ʼʱ��</param>
	// / <param name="timestampStyleTicks">ʱ����̶�ֵ</param>
	// / <returns>long</returns>
	private long GetTimestamp(Date startDateTime, long timestampStyleTicks)
			throws Exception {
		if (timestampStyleTicks <= 0)
			throw new Exception("ʱ����̶���ʽ����ֵ����������Ϊ0����");

		Date endDateTime = new Date();

		long a = endDateTime.getTime() - startDateTime.getTime();

		long ticks = (endDateTime.getTime() - startDateTime.getTime())
				/ timestampStyleTicks;

		return ticks;
	}

	// / <summary>
	// / ��̬�����������
	// / </summary>
	private static Random random;

	// / <summary>
	// / ��ȡ�����
	// / </summary>
	// / <param name="length">���������</param>
	// / <returns></returns>
	private long GetRandom(int length) throws Exception {
		if (length <= 0)
			throw new Exception("������������ô��󣬳��ȱ������0");

		if (random == null)
			random = new Random();

		int maxValue = Integer.valueOf(pow(10, length) + "");
		long result = Long.valueOf(random.nextInt(maxValue) + "");
		return result;
	}

	/**
	 * ��arg0��arg1��
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	private Long pow(int arg0, int arg1) {
		long value = arg0;
		for (int i = 0; i < arg1; i++) {
			value *= arg0;
		}
		return value;
	}
}
