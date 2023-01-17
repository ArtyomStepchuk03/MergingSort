import java.util.ArrayList;
import java.util.List;

public record DataSorter(boolean sortMode) {

    private List<String> mergeString(List<String> first, List<String> second) {
        if (first == null || second == null) {
            try {
                return second;
            } catch (NullPointerException e) {
                return first;
            }
        }

        List<String> result = new ArrayList<>();
        int firstSize = first.size();
        int secondSize = second.size();
        int positionRight = 0, positionLeft = 0;

        for (int i = 0; i < firstSize + secondSize; i++) {
            String left;
            String right;
            try {
                left = first.get(positionLeft);
            } catch (IndexOutOfBoundsException e) {
                while (positionRight < secondSize) {
                    result.add(second.get(positionRight));
                    positionRight++;
                }
                return result;
            }

            try {
                right = second.get(positionRight);
            } catch (IndexOutOfBoundsException e) {
                while (positionLeft < firstSize) {
                    result.add(first.get(positionLeft));
                    positionLeft++;
                }
                return result;
            }

            try {
                if (left.compareTo(right) > 0 == sortMode) {
                    result.add(right);
                    positionRight++;
                } else {
                    result.add(left);
                    positionLeft++;
                }
            } catch (NullPointerException ignored) {}
        }
        return result;
    }

    private int[] mergeInt(int[] first, int[] second) {
        if (first == null || second == null) {
            try {
                return second;
            } catch (NullPointerException e) {
                return first;
            }
        }

        int firstSize = first.length;
        int secondSize = second.length;
        int[] result = new int[firstSize + secondSize];
        int positionRight = 0, positionLeft = 0;

        // TODO добавить проверку каждой строчки на вместимть int

        for (int i = 0; i < result.length; i++) {
            if (positionLeft == firstSize) {
                try {
                    result[i] = second[positionRight];
                    positionRight++;
                    continue;
                } catch (IndexOutOfBoundsException e) {
                    return result;
                }
            }
            if (positionRight == secondSize) {
                try {
                    result[i] = first[positionLeft];
                    positionLeft++;
                    continue;
                } catch (IndexOutOfBoundsException e) {
                    return result;
                }
            }
            int left = first[positionLeft];
            int right = second[positionRight];

            if (left > right == sortMode) {
                result[i] = right;
                positionRight++;
            } else {
                result[i] = left;
                positionLeft++;
            }

        }
        return result;
    }








    public List<String> sortString(List<String> list) {
        if (list == null)
            return null;

        int size = list.size();

        if (size == 1)
            return list;

        int half = size / 2;

        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();
        for (int i = 0; i < half; i++) first.add(list.get(i));
        for (int i = half; i < size; i++) second.add(list.get(i));

        if(first.isEmpty() && second.isEmpty())
            return null;
        return mergeString(sortString(first), sortString(second));
    }

    public int[] sortInt(int[] list) {
        if (list == null)
            return null;

        int size = list.length;

        if (size == 1)
            return list;

        int half = size / 2;

        int[] first = new int[half];
        int[] second = new int[size - half];

        System.arraycopy(list, 0, first, 0, half);
        System.arraycopy(list, half, second, 0, size - half);

        return mergeInt(sortInt(first), sortInt(second));
    }
}