import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

// Класс для игрушек
class Toy implements Comparable<Toy> {
    private int id;
    private String name;
    private int weight;

    public Toy(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Toy other) {
        return Integer.compare(other.weight, this.weight); // Чем больше вес, тем выше приоритет
    }

    @Override
    public String toString() {
        return id + " " + name;
    }
}

// Основной класс розыгрыша
public class ToyLottery {
    // Очередь для игрушек
    private PriorityQueue<Toy> queue;

    public ToyLottery() {
        queue = new PriorityQueue<>();
    }

    // Добавляем игрушки в очередь
    public void addToy(Toy toy) {
        queue.add(toy);
    }

    // Метод для случайного получения игрушки с учетом веса
    public Toy getToy() {
        int totalWeight = 0;
        for (Toy toy : queue) {
            totalWeight += toy.getWeight();
        }

        Random random = new Random();
        int randomValue = random.nextInt(totalWeight);

        int currentWeight = 0;
        for (Toy toy : queue) {
            currentWeight += toy.getWeight();
            if (randomValue < currentWeight) {
                return toy;
            }
        }
        return null; // На случай ошибки
    }

    // Метод для проведения 10 розыгрышей и записи результатов в файл
    public void runLottery(String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for (int i = 0; i < 10; i++) {
            Toy selectedToy = getToy();
            if (selectedToy != null) {
                writer.write("Результат " + (i + 1) + ": " + selectedToy + "\n");
                System.out.println("Результат " + (i + 1) + ": " + selectedToy);
            }
        }
        writer.close();
    }

    public static void main(String[] args) {
        ToyLottery lottery = new ToyLottery();
        
        // Добавляем игрушки
        lottery.addToy(new Toy(1, "Конструктор", 2));  // 20%
        lottery.addToy(new Toy(2, "Робот", 2));        // 20%
        lottery.addToy(new Toy(3, "Кукла", 6));        // 60%

        try {
            lottery.runLottery("lottery_results.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
