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

    private List<Integer> mergeInteger(List<Integer> first, List<Integer> second) {
        if (first == null || second == null) {
            try {
                return second;
            } catch (NullPointerException e) {
                return first;
            }
        }

        List<Integer> result = new ArrayList<>();
        int firstSize = first.size();
        int secondSize = second.size();
        int positionRight = 0, positionLeft = 0;

        for (int i = 0; i < firstSize + secondSize; i++) {
            Integer left;
            Integer right;
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
                if (left > right == sortMode) {
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

    public List<String> sortString(List<String> list) {
        if (list == null) return null;

        int size = list.size();

        if (size == 1) return list;

        int half = size / 2;

        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();

        for (int i = 0; i < half; i++) first.add(list.get(i));
        for (int i = half; i < size; i++) second.add(list.get(i));

        if(first.isEmpty() && second.isEmpty()) return null;

        return mergeString(sortString(first), sortString(second));
    }

    public List<Integer> sortInteger(List<Integer> list) {
        if (list == null) return null;

        int size = list.size();

        if (size == 1) return list;

        int half = size / 2;

        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();

        for (int i = 0; i < half; i++) first.add(list.get(i));
        for (int i = half; i < size; i++) second.add(list.get(i));

        if(first.isEmpty() && second.isEmpty()) return null;

        return mergeInteger(sortInteger(first), sortInteger(second));
    }
}