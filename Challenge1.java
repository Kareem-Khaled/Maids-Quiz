import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Challenge1 {
    public static List<int[]> findPairs(int[] nums, int target) {
        List<int[]> result = new ArrayList<>();
        int n = nums.length;
        Pair[] pairs = new Pair[n];
        
        for (int i = 0; i < n; i++) {
            pairs[i] = new Pair(nums[i], i);
        }
        
        // Sort the pairs based on the values
        Arrays.sort(pairs, (a, b) -> Integer.compare(a.value, b.value));
        
        for (int i = 0; i < n; i++) {
            int x = target - pairs[i].value;
            int index = binarySearch(pairs, x, i + 1);
            if (index != -1) {
                result.add(new int[]{pairs[i].index, pairs[index].index});
            }
        }
        
        return result;
    }

    private static int binarySearch(Pair[] pairs, int target, int start) {
        int left = start;
        int right = pairs.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (pairs[mid].value == target) {
                return mid;
            } else if (pairs[mid].value < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
		int[] nums = { 3, 2, 1, 4, 7, 5 };
		int target = 6;

        List<int[]> pairs = findPairs(nums, target);

        System.out.println("Array indices of the two numbers whose sum equals " + target + " are:");
        for (int[] pair : pairs) {
            System.out.println(Arrays.toString(pair));
        }
    }
    
    static class Pair {
        int value;
        int index;
        
        public Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }
}
