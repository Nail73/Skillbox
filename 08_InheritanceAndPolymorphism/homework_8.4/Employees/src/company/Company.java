package company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Company {

  private final List<Employee> employees = new ArrayList<>();

  public void hire(Employee employee) {
    employee.setCompany(this);
    employees.add(employee);
  }

  public void hireAll(List<Employee> employee) {
    for (Employee e : employee) {
      hire(e);
    }
  }

  public void fire(Employee employee) {
    employee.setCompany(null);
    employees.remove(employee);
  }

  public int getIncome() {
    int income = 0;
    for (Employee e : employees) {
      if (e instanceof Manager) {
        income += ((Manager) e).getSales();
      }
    }
    return income;
  }

  public List<Employee> getTopSalaryStaff(int count) {
    return getList(count, Comparator.reverseOrder());
  }

  public List<Employee> getLowestSalaryStaff(int count) {
    return getList(count, Comparator.naturalOrder());
  }

  private List<Employee> getList(int count, Comparator<Employee> comparator) {
    if (count < 0) {
      return Collections.emptyList();
    }
    if (count < employees.size()) {
      count = count;
    }
    employees.sort(comparator);
    return new ArrayList<>(employees.subList(0, count));
  }

  public List<Employee> getEmployees() {
    return new ArrayList<>(employees);
  }
}
