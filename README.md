## FinSync Project Description

### Overview
The FinSync project is a comprehensive financial management system designed to aggregate and analyze a user's financial data from multiple sources. This Spring Boot application integrates various financial entities such as bank accounts, deposits, loans, mutual funds, and stocks, providing a consolidated view of a user's financial health.

### Request Structure
The API accepts a JSON request containing five key sections: accounts, deposits, loans, mutual funds, and stocks. Each section includes relevant details for each financial entity. Here's a breakdown of each section:

1. **Accounts**: 
   - **accountNumber**: Unique identifier for the bank account.
   - **bankName**: Name of the bank.
   - **balance**: Current balance in the account.

2. **Deposits**:
   - **accountNumber**: Unique identifier for the deposit account.
   - **type**: Type of deposit (e.g., Fixed Deposit).
   - **amount**: Amount invested in the deposit.

3. **Loans**:
   - **accountNumber**: Unique identifier for the loan account.
   - **type**: Type of loan (e.g., personal, agriculture).
   - **outstandingAmount**: Current outstanding amount on the loan.
   - **principleAmount**: Principal amount of the loan.

4. **Mutual Funds**:
   - **dematAccountNumber**: Unique identifier for the demat account.
   - **name**: Name of the mutual fund.
   - **units**: Number of units held in the mutual fund.
   - **nav**: Net Asset Value of the mutual fund.

5. **Stocks**:
   - **dematAccountNumber**: Unique identifier for the demat account.
   - **name**: Name of the stock.
   - **quantity**: Number of shares held.
   - **price**: Price per share.

### Response Structure
The API response provides a summary of the user's financial status, including savings, investments, and net worth. Here's a breakdown of the response fields:

1. **totalSavingsInBank**: Total balance across all bank accounts.
2. **totalSavingsInDeposit**: Total amount invested in deposits.
3. **totalLoanAmountPending**: Total outstanding amount across all loans.
4. **totalNetWorth**: User's total net worth, calculated as the sum of all assets minus liabilities.

5. **stock**:
   - **totalInvested**: Total amount invested in stocks.
   - **currentValue**: Current market value of the stocks.
   - **gain**: Total gain from stock investments (current value minus invested amount).

6. **mutualFund**:
   - **totalInvested**: Total amount invested in mutual funds.
   - **currentValue**: Current market value of the mutual funds.
   - **gain**: Total gain from mutual fund investments (current value minus invested amount).

### Calculation Details
- **totalSavingsInBank**: Sum of balances from all bank accounts.
- **totalSavingsInDeposit**: Sum of amounts from all deposit accounts.
- **totalLoanAmountPending**: Sum of outstanding amounts from all loan accounts.
- **totalNetWorth**: Calculated as the sum of total savings in bank, total savings in deposit, current value of stocks, and current value of mutual funds, minus the total loan amount pending.
- **stock.currentValue**: Calculated as the sum of (quantity * price) for all stocks.
- **mutualFund.currentValue**: Calculated as the sum of (units * nav) for all mutual funds.
  
### Project Benefits
- **Comprehensive Financial Overview**: Aggregates data from multiple sources to provide a unified view of a user's financial status.
- **Real-time Insights**: Offers up-to-date calculations of net worth, investment gains, and loan liabilities.
- **Investment Tracking**: Monitors the performance of stocks and mutual funds, helping users make informed investment decisions.

### Technology Stack
- **Backend**: Spring Boot
- **Data Aggregation**: JSON-based API request handling
- **Response Calculation**: Real-time computation of financial metrics

The FinSync project aims to simplify financial management by providing users with a detailed and holistic view of their financial landscape, enabling better decision-making and financial planning. 
