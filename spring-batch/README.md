**_SPRING BATCH COURSE SYLLABUS_**
1) Spring Batch Introductory application. Simple spring reader-writer application  
2) Spring Batch Architecture and Domain Language (Terminology)  
3) Spring Batch Meta-Data  
4) Configuration of Job and restartable setting  
5) Job Interception  
6) Job Inheritance and JobParameterValidator  
7) Java Configuration  
8) Job Repository , Transaction Configuration , Changing Table prefix   
9) JobLauncher  
	a) Running Job  
	b) From Command Line  
	c) From Web Container  
10) Advance Meta-Data Usage  
	a) Querying the repository  
	b) Job Registry  
	c) JobOperator  
	d) JobParametersIncrementer  
	e) Stopping and aborting job  

**_Examples_**  
1) CSV file to Database  
2) Database to CSV file  
3) XML File to Database  
4) Database to XML File  
5) MultiResourceItemReader : to read multiple files and write them to specific one thing (CSV or DB)  
6) Scheduling Batch Job  
7) Tasklet Interface : to do a single step job like cleaning or initializing resource  
8) Spring Batch Partitioning : Span multiple threads to do the same job inorder to reduce the time taken by job  