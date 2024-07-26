import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input Map
        List<String> mapInput = new ArrayList<>();
        while (true) {
            String line = sc.nextLine();
            if (line.equals("OK")) {
                break;
            }
            mapInput.add(line);
        }

        int rows = mapInput.size();
        int cols = mapInput.get(0).length();
        char[][] map = new char[rows][cols];

        int startX = 0, startY = 0, endX = 0, endY = 0;
        boolean startFound = false, endFound = false;

        // Inisiasi titik start dan end
        for (int i = 0; i < rows; i++) {
            map[i] = mapInput.get(i).toCharArray();
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == '^') {
                    startX = i;
                    startY = j;
                    startFound = true;
                }
                if (map[i][j] == '*') {
                    endX = i;
                    endY = j;
                    endFound = true;
                }
            }
        }

        if (!startFound || !endFound) {
            System.out.println("Invalid map input: missing start or end point.");
            return;
        }

        findPath(map, startX, startY, endX, endY, rows, cols);
    }

    private static void findPath(char[][] map, int startX, int startY, int endX, int endY, int rows, int cols) {
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();

        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0], y = current[1];

            if (x == endX && y == endY) {
                printPath(parentMap, startX, startY, endX, endY);
                return;
            }

            // Atas
            if (x > 0 && map[x - 1][y] != '#' && !visited[x - 1][y]) {
                queue.add(new int[]{x - 1, y});
                visited[x - 1][y] = true;
                parentMap.put((x - 1) + "," + y, x + "," + y + "," + "atas");
            }

            // Kebawah
            if (x < rows - 1 && map[x + 1][y] != '#' && !visited[x + 1][y]) {
                queue.add(new int[]{x + 1, y});
                visited[x + 1][y] = true;
                parentMap.put((x + 1) + "," + y, x + "," + y + "," + "bawah");
            }

            // Kiri
            if (y > 0 && map[x][y - 1] != '#' && !visited[x][y - 1]) {
                queue.add(new int[]{x, y - 1});
                visited[x][y - 1] = true;
                parentMap.put(x + "," + (y - 1), x + "," + y + "," + "kiri");
            }

            // Kanan
            if (y < cols - 1 && map[x][y + 1] != '#' && !visited[x][y + 1]) {
                queue.add(new int[]{x, y + 1});
                visited[x][y + 1] = true;
                parentMap.put(x + "," + (y + 1), x + "," + y + "," + "kanan");
            }
        }

        System.out.println("tidak ada jalan");
    }

    private static void printPath(Map<String, String> parentMap, int startX, int startY, int endX, int endY) {
        LinkedList<String> path = new LinkedList<>();
        String currentKey = endX + "," + endY;
        String previousDirection = null;
        int count = 0;

        while (!currentKey.equals(startX + "," + startY)) {
            String value = parentMap.get(currentKey);
            if (value == null) break;
            String[] parts = value.split(",");
            String parentKey = parts[0] + "," + parts[1];
            String direction = parts[2];

            if (direction.equals(previousDirection)) {
                count++;
            } else {
                if (previousDirection != null) {
                    path.addFirst(count + " " + previousDirection);
                }
                previousDirection = direction;
                count = 1;
            }

            currentKey = parentKey;
        }

        if (previousDirection != null) {
            path.addFirst(count + " " + previousDirection);
        }

        int totalSteps = 0;
        for (String step : path) {
            System.out.println(step);
            String[] parts = step.split(" ");
            totalSteps += Integer.parseInt(parts[0]);
        }
        System.out.println(totalSteps + " langkah");
    }
}