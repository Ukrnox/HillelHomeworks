package com.hillel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int dif = target - nums[i];
            if (map.containsKey(dif)) {
                Integer first = map.get(dif);
                return new int[]{first, i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
        int target = 9;
        int[] nums = {2, 7, 11, 15};
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }
}
