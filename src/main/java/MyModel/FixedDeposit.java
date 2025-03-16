package MyModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class FixedDeposit {
    @Id
    private Long id;

    @Column(nullable = false)
    private int accountId;

    @Column(nullable = false)
    private double principalAmount;

    @Column(nullable = false)
    private double interestRate;

    @Column(nullable = false)
    private int tenure; // In months

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate maturityDate;

    @Column(nullable = false)
    private double maturityAmount;

    @Column(nullable = false)
    private String status;

    public FixedDeposit() {}

    public FixedDeposit(Long fdid, int accountId, double principalAmount, double interestRate, int tenure, String status) {
        this.id = fdid;
        this.accountId = accountId;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenure = tenure;
        this.startDate = LocalDate.now();
        this.maturityDate = startDate.plusMonths(tenure);
        this.maturityAmount = calculateMaturityAmount();
        this.status = status;
    }

    public FixedDeposit(String test, Long id, int accountId, double principalAmount, double interestRate, int tenure, LocalDate startDate, LocalDate maturityDate, double maturityAmount, String status) {
        this.id = id;
        this.accountId = accountId;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenure = tenure;
        this.startDate = startDate;
        this.maturityDate = maturityDate;
        this.maturityAmount = maturityAmount;
        this.status = status;
    }

    private double calculateMaturityAmount() {
        double interest = principalAmount * (interestRate / 100) * (tenure / 12.0);
        return principalAmount + interest;
    }

    public double calculateEarlyWithdrawalAmount(double penaltyRate) {
        long monthsElapsed = ChronoUnit.MONTHS.between(startDate, LocalDate.now());
        if (monthsElapsed <= 0) {
            return principalAmount;
        }

        double adjustedInterest = principalAmount * (penaltyRate / 100) * (monthsElapsed / 12.0);
        return principalAmount + adjustedInterest;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public double getPrincipalAmount() { return principalAmount; }
    public void setPrincipalAmount(double principalAmount) { this.principalAmount = principalAmount; }
    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
    public int getTenure() { return tenure; }
    public void setTenure(int tenure) { this.tenure = tenure; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getMaturityDate() { return maturityDate; }
    public void setMaturityDate(LocalDate maturityDate) { this.maturityDate = maturityDate; }
    public double getMaturityAmount() { return maturityAmount; }
    public void setMaturityAmount(double maturityAmount) { this.maturityAmount = maturityAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
