# Ideas

## Concepts

### Transactions
List of:
- asset variation
- liability variation
The unbalance creates a:
- gain (revenue)
- loss (expenditure)

A transaction has to respect the following invariant:
```
asset variation - liability variation = revenue - expenditure
```

Every variation has a date

### Accounts
Accounts are type of either:
- asset
- liability
- revenue
- expenditure

Accounts info:
- currency (uneditable afterwards)
- name
- parent (asset/liability/revenue/expenditure, if none)

This creates 4 independent tree structures

### Currencies

https://www.bancaditalia.it/compiti/operazioni-cambi/Operating_Instructions.pdf?language_id=1

There is a permanent main currency and then the other currencies

Between currencies there is a exchange ratio that changes day by day, 
so the transaction is ideally registered with the ratio in that day.

A transaction in a "foreign" currency must be immediately evaluated in the main currency.
Everytime we exchange the "foreign" currency for the main currency, we register a transaction that
removes the "foreign" asset, adds a main currency asset, and generates a gain/loss in main currency.

Effectively, any "foreign" currency is considered as a value-holding asset, like a car or a piece of
jewelry. We either re-evaluate it, or we register at the selling (conversion) moment.

Exchange rate in the conversion are either put manually, or by retrieving them automatically.

### Time as currency

We can consider time as currency in different ways:
- `income / time` -> life value
  - `1500€ = 30d => d/50 = 1€`
    - `y/365 = mo/30 = w/7 = 1d = 24h = 1440m = 86400s`
- `income / worktime` -> pro value
  - `1500€ = 22d => 1d = 68.19€`
    - `y/260 = mo/22 = w/5 = 1d = 8h = 480m = 28800s`
- `expenses / time` -> life cost
  - `1200€ = 30d => 1d = 40€`
    - `y/365 = mo/30 = w/7 = 1d = 24h = 1440m = 86400s`
- `(income - expenses) / expenses` -> life accumulation
  - `1500€/1200€ - 1 => 0,25`
    - `y/365 = mo/30 = w/7 = 1d = 24h = 1440m = 86400s`

Example:
- 3000€ earned in 30d (21d job) and 1300€ spent in 30d mean:
  - life value: 100€ = 1d -> 4.16€ = 1h
  - pro value: 142€ = 1d -> 17.75€ = 1h
  - life cost: 43€ = 1d -> 1.79€ = 1h
  - life accumulation: 100€ / 43€ - 1 -> 1,32

## All possible screens

### Splash page
Possible background loading and other initialization

### Login/unlock page
Just to avoid opening the app by everyone

### Main page
Common elements:
- bottom navigation bar

#### Statistics
Core concepts:
- assets / liabilities
- expenditures / revenues
- swipe to change view
- shortcut to add transaction

##### Hierarchical revenue chart
Only for expenditures and revenues
Filters:
- date range
- type of expenditure/revenue
Tabs for expenditure vs revenue

##### Hierarchical asset chart
Only for asset/liability
Filters:
- type of asset/liability
- end date
Tabs for asset vs liability

##### Bar revenue chart
Only for expenditures and revenues
Filters:
- date range and resolution
- type of expenditure/revenue
Tabs for expenditure vs revenue vs net income

##### Bar asset chart
Only for asset and liability
Filters:
- date range and resolution
- type of asset/liability
Tabs for asset vs liability vs net worth

##### Expenditure/revenue distribution
Where do the earned money go?
Roughly: revenue - expenditures...?

##### Time stats
Statistics, but using time as a currency

#### Budgeting/prediction

##### Start/stop expected transactions
Frequency:
- One time
- periodic:
  - every x days/weeks/months/years
  - at moment x in day/week/month
Transaction components

##### Compare with expenditure/revenues
Show deltas over selectable period of time
  
#### Transactions
A transaction is a list of:
- assets/liabilities variations in different point in times
- any unbalance automatically adds either (in a selectable date)
  - a revenue if positive (+assets -liability)
  - a expenditure if negative (-assets +liability)

##### Filterable list of the transactions
A row is a transaction that can be expanded to see its components

Filter by:
- type of asset/expenditure/revenue/liability
- date range
- involved account

##### Add a transaction
- (Optional) choose a template
- add components
- edit components amounts
- add relative documents and notes, if any
- save

##### Remove a transaction
- confirmation dialog

##### Create templates
- select components
- it automatically adds any unbalance compensation (revenue/expenditure)
  - choose the revenue or expenditure type

#### Accounts
Accounts (or types) are tree structures

##### List of expenditures, revenues, assets, liabilities
Those 4 are the trees roots

##### Add an account
- type
- name
- parent (if any)

##### Edit an account
- type
- name
- parent (if any) <- not downwards move (check if it happens)

#### Settings

##### Time currency settings
Either:
- automatic estimation on `life value`, `pro value`, `life cost` and `life gain`
- custom unit, fixed exchange rate

Anxiety mode:
- notify every x-tick how much that time interval has cost

##### Backup
Import / export
- format
  - csv
  - sqlite
  - google sheet
- where
  - internal storage
  - google drive, fixed directory
