#Customs Vehicle Clearance Automation With Java, Spring framework and Camunda BPM

    ---
    This project will show to use Java, Spring framework and Camunda BPM to deliver an end-to-end paperless automation to Customs Vehicle Clearance process.
    What inspired me to build this project was my adverse experience at local customs clearance, I spent 1 week trying clear my vehicle from one country to the other.
    ---

    ## The Business Case for Customs Vehicle Clearance Automation
    ---
    If a person(customer) has stayed in a country A for about 3years or more and you are originally from country B, if you want to export a vehicle as a returning resident from A to B, you are entitled to 100% duty free.
    The customer is required to submit a number of documents from country A at a port of entry to country B:
    Police clearance
    sapco document from vehicle registry authority
    ITac from department of trade.
    Proof of ownership.
    Passport(s)
    3 month Bank statements
    3 month salary
    Resignation letters
    Proof of address

    On arrival at a port of entry of a country B, the customer goes to customs section and waits in a queue inorder to be processed. It normally takes a customs office and a supervisor a minimum of
    2hours to process a customer, i.e. a maximum 12 people are processed per day(24hours). This processing involves the customs officer going through customer's documents, consulting valuation tables and web sites,
    filling a lot of forms manually, physically inspecting the vehicle, writing recommendations for the supervisor and submitting all the completed forms and customer's documents to the supervisor.
    The supervisor then goes through these and does his own writing and prints an invoice, and hands these back to the customs officer. The customs officer then does more manual writing and stamps some forms and hands these to the customers, and tells her/him what to do.
    If the customer is missing some information, then all the steps done above will have to be redone from start, normally by another shift.
    There seems to be preferential treatment of other people.
    There is seem to a lot of usage of paper and manual writing.
    The staff seems overworked.
    The number of customers processed seems very small.

    ---

    ## Case for Camunda BPM

    ---
    BPM encourages collaborative solutioning, i.e. solving a business problem before writing code. This may evolve understanding high-level strategic goals and expressing these as KPI and SLA,
    e.g. Zero usage of paper, processing a customer in 5minutes instead of days. The next step could then be process discovery inorder to map the business process end-to-end,this may give the process model below.

    ---
    [![Customs Process](/src/main/resources/customs_process.png)]

    Figure 1

    ## Development Process
    ---
    After agreeing with stake holders in terms of the model in figure 1, we would break it into iterations 0, 1, 2 ..., and deliver agreed features in each iteration. iteration 0 might involve delivering a working skeleton model
    to help us understand the problem domain. The nice thing with Camunda is that it helps us to acheive this quickly with minimal initial code.
    ---

    ## Testing
    ---
    Once I have a model of the business process, I like to start driving the implementation with tests, this you can do with junit.
    ---

    ## Running Application

    Email
    I used fakeSMTP as local smtp server, you will need one to run the solution.

    1. Run mvn spring-boot:run
    2. Browse on http://localhost:8080
    3. Login as demo and password demo
    4. Run test using mvn test.


