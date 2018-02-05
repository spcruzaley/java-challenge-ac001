# Java Challenge


## Please read first 


We usually ask candidates to deliver this assessment within 3 days.

If you are unable to take 3 days to work on this assessment, please let your recruiter know
beforehand. If you are busy at work, our recommendation is that you ask to do this test on a
weekend. We are very flexible as long as you provide a reasonable explanation.
  
  Not having enough time will not be accepted as an excuse to deliver an unsatisfactory solution.
  
  
  ## Background
  
  
Lannister Carriage Services provides commute and transportation services to a number of
towns in the great land of Westeros. Because of economical reasons and to avoid ambushes,
most routes are "oneway".
That is, a route from Volantis to King's Landing does not imply in a
route from King's Landing to Volantis. In fact, even if both of these routes do happen to exist,
they are distinct and are not necessarily the same distance!


## Story Phase


As a Lannister Carriage Services customer I want to know the available routes between towns
as well as their distances so I can choose the best route for my travel.


## Business Narrative/Scenario

The purpose of this application is to help Lannister Carriage Services provide its customers
information about the routes. In particular, you will compute the distance along a certain route,
the number of different routes between two towns, and the shortest route between two towns.


## Functional / Acceptance Criteria

The input will be given as a directed graph where a node represents a town and an edge represents a route between two towns. The weighting of the edge represents the distance between the two towns. A given route will never appear more than once, and for a given route, the starting and ending town will not be the same town.
The directed graph will be represented as plain text, where the towns are named using letters from the alphabet. A route from town A to town B with distance 5 is represented by the string **AB5**.

It also can be represented as JSON:

```javascript
{ 
  "source": "A",
  "target": "B",
  "distance":5
}
```

For example, complete scenario ca be represented by the following string:
**AB6, AE4, BA6, BC2, BD4, CB3, CD1, CE7, DB8, EB5, ED7**.


and as JSON :

```javascript
{
  "data":[
  { 
    "source": "A", "target": "B", "distance":6
  },
  { 
    "source": "A", "target": "E", "distance":4
  },
  { 
    "source": "B", "target": "A", "distance":6
  },
  { 
    "source": "B", "target": "C", "distance":2
  },
  { 
    "source": "B", "target": "D", "distance":4
  },
  { 
    "source": "C", "target": "B", "distance":3
  },
  { 
    "source": "C", "target": "D", "distance":1
  },
  { 
    "source": "C", "target": "E", "distance":7
  },
  { 
    "source": "B", "target": "D", "distance":8
  },
  { 
    "source": "E",  "target": "B", "distance":5
  },
  { 
    "source": "E", "target": "D", "distance":7
  }
]}
```

  
  For any given directed graph, the application should be able to:
1. Compute the total distance of any route given as input. A route will be represented as a
string of town letters, such as "ABC".
In the example above, the distance of route
"ABCD"
is 6 + 2 + 1 = 9 and the distance of route "AE"
is 4.
2. Compute all available routes from any given pair of towns within a given maximum number
of stops. In the example graph, for starting town A and ending town C there are an infinite
number of routes: "ABC",
"AEBC",
"AEDBC",
"AEDBDBC",
"AEDBDBDBC",
and so on. However, if we limit this list to routes with 3 stops or
less, the result will be routes "ABC"
(2 stops) and "AEBC"
(3 stops) only. If no routes
are available, the application should explicitly indicate that.
3. Compute the shortest route (in terms of distance to travel) between any pair of towns given
as input. In the example graph, for starting town A and ending town C the shortest route is
"ABC",
which has a travel distance of 8. If no routes are available, the application should
explicitly indicate that.

## Test Data

Input graph:
AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7

Test cases:
1. Distance of route ABC: 9
2. Distance of route AD: 5
3. Distance of route ADC: 13
4. Distance of route AEBCD: 22
5. Distance of route AED: NO SUCH ROUTE
6. Routes starting at C and ending at C with a maximum of 3 stops: 
	- CDC  (2 stops)
	- CEBC (3 stops)
7. Routes starting at A and ending at C with a maximum of 4 stops:
	- ABC   (2 stops)
	- ADC   (2 stops)
	- AEBC  (3 stops)
	- ABCDC (4 stops)
	- ADCDC (4 stops)
	- ADEBC (4 stops)
8. Shortest route (by distance) from A to C: ABC  (distance = 9)
9. Shortest route (by distance) from B to B: BCEB (distance = 9)


## Technical Details


You should implement more than a barebone algorithm. We are expecting a runnable application with a minimal structure. You should create an object model and use design patterns wherever they are appropriate, but try to keep things simple.

1. Maven or gradle must be used to build, run tests and start the application. 
2. The tests must be started with the mvn test command or similar. 
3. The application must start with a Maven command or similar: mvn exec:java, mvn jetty:run, mvn spring-boot:run, etc. 
4. The application must have a stateless API and use a database to store data. 
5. An embedded in-memory database should be used: either H2, HSQL, SQLite or Derby. 
6. The database and tables creation should be done by Maven/Graddle or by the application. 
7. You must provide gitlab username. A free gitlab account can be created at http://gitlab.org. Once finished, you must give the user ac-recruitment read permission on your repository so that you can be evaluated. 
8. You must provide a README.txt (plain text) or a README.md (Markdown) file at the root of your repository, explaining: 
- How to compile and run the application with an example for each call. 
- How to run the suite of automated tests. 
- Mention anything that was asked but not delivered and why, and any additional comments.

## Assessment Guidelines

You will be assessed on the following aspects, sorted by priority:
1. Code cleanness and naming consistency
2. Object orientation design
3. Automated tests (unit and/or integration tests)
4. Appropriate use of the language, framework features and best practices
5. Correct execution
6. Feature completeness

Please keep in mind that, for this assessment, it is more important that you deliver quality code than a feature complete implementation. Your code will still be evaluated even if you couldn't implement all the acceptance criteria.
You're expected to complete this assessment without anyone's help. We will ask questions about your code in an interview. **Play fairly!**

