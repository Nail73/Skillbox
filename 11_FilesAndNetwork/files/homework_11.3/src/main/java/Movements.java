import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Movements {

  private double incomeSum = 0.0;
  private double expenseSum = 0.0;
  private final Map<String, Double> expenseList = new HashMap<>();

  public Movements(String pathMovementsCsv) {

    List<String> lines = null;
    try {
      lines = Files.readAllLines(Paths.get(pathMovementsCsv));
    } catch (IOException e) {
      e.printStackTrace();
    }
    for (int i = 1; i < (lines != null ? lines.size() : 0); i++) {
      System.out.println(CalculateData(lines.get(i)));
    }
  }

  public String CalculateData(String input) {

      String[] columns = input.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

      double incomeTmp = Double.parseDouble(columns[6].replaceAll("\"", "").replace(",", "."));
      double expenseTmp = Double.parseDouble(columns[7].replaceAll("\"", "").replace(",", "."));
      if (incomeTmp == 0) {
        calculateExpense(columns[5], expenseTmp);
      } else {
        this.incomeSum += incomeTmp;
      }

     return input;
  }

  public double getIncomeSum() {
    return incomeSum;
  }

  public double getExpenseSum() {
    return expenseSum;
  }

  private void calculateExpense(String expenseName, double expenseSum) {
    this.expenseSum += expenseSum;
    String[] transaction = expenseName.trim().split(" {3,}");
    String[] temp = transaction[1].split("/");
    String[] suppliesTemp = temp[temp.length - 1].split("\\\\");
    String supplies = suppliesTemp[suppliesTemp.length - 1];
    if (!expenseList.containsKey(supplies)) {
      expenseList.put(supplies, expenseSum);
    } else {
      double sum = expenseList.get(supplies);
      sum += expenseSum;
      expenseList.put(supplies.trim(), sum);
    }
  }

  public void printData() {
    System.out.println("ДВИЖЕНИЕ ДЕНЕЖНЫХ СРЕДСТВ");
    System.out.printf("ОБЩИЕ ПОСТУПЛЕНИЕ НА СЧЕТ: %.2f руб\n", getIncomeSum());
    System.out.println("--------------------------------------------------");
    System.out.println("ТАБЛИЦА РАСХОДОВ:");

    for (String supplies : expenseList.keySet()) {
      System.out.printf("%-30s -----> %.2f %-10s\n", supplies, expenseList.get(supplies), "руб");
    }
    System.out.println("--------------------------------------------------");
    System.out.printf("ОБЩИЙ РАСХОД: %.2f руб", getExpenseSum());
  }
}