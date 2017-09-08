package com.zzsim.taxi.admin.util;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

public class UniqueUtil {
	
	private static LongAdder adderNo = new LongAdder();
	private static long no = Instant.now().getEpochSecond();


}
