DESCRIPTION: This project  will attempt to answer the million dollar question: Who will win this year's Super Bowl? It will accomplish this by incorporating various stats and formulas. Scores, player stats, and probabilities are the meat and bones of this terminal based project! Follow these instructions in the terminal upon compiling the java file (nflPlayoffSimulator.java). Enter the following into the command line to produce the desired effect




method 1: view the real life results of each playoff game to compare my simulations -> cat ActualResults.txt

    to make sure I tried my best to predict the playoffs as accurately as possible,
    refer to this text file and see how the results match

method 2: view the vegas odds -> cat (or type for Windows) VegasOdds.txt

    this will give you an idea of who oddmakers gave the best chances of winning so you can compare my results 

method 3: view the 2017 playoff bracket -> java nflPlayoffSimulator

    this is just a way to view who the 12 participating teams are 

method 4: view a table of super bowl win likelihood percentage by each team -> java nflPlayoffSimulator table

    this was done by simulating the playoffs 100x and listing the amount of timess 
    a team won the super bowl each simulation
    the table should also give you a good idea of which teams should perform the best in the simulations
 
method 5: view likelihoods of the 4 possible super bowl matchups -> java nflPlayoffSimulator final4

    as of 1/17/17 (due date of final commit), there are 4 teams left in the playoffs, and thus
    there are 4 possible Super Bowl matchups
    this method will print the probabilities of each of the 4 possible matchups occuring
  
method 6: view the complete results of a simulation! -> java nflPlayoffSimulator simulation

    simulates the entire playoffs from start to finish, posting the scores, stats, and probabilities of each game
    total of 11 games 

method 7: simulate the real life 2nd round and beyond -> java nflPlayoffSimulator simDiv

    presets the matchups for the 2nd round to the ones that were actually played in real life
  
    it will simulate the rest of the playoffs from that point on

    for reference: Patriots vs Texans, Chiefs vs Steelers, Cowboys vs Packers, Falcons vs Seahawks

    during the week of presentations, this method should emulate the results of the real life 2nd round accurately 

method 8: simulate the real life semifinals and beyond -> java nflPlayoffSimulator simCC
  
    presets the final 4 teams remaining in real life during the week of presentations

    for reference: Patriots vs Steelers, Falcons vs Packers


FINAL THOUGHTS ON THE PROJECT: 
This project can be reused during the next playoffs by inserting the proper text files of stats for next season, but we have to wait till then... While this project took a completely mathematical approach, the game of football can be very wild at times and defies logic. Players can break out of nowhere, inexpliciable plays happen, and usually dominant teams can have a bad day. Probabilities are the basis of the project. Sometimes, the underdog wins in real life! And it can very well happen in the simulations

-While this project is simple in idea, it was not so simple to execute. The development has been frustrating at times but was also incredibly satisfying once it was complete. I hope this project will help you better understand how important statistics and numbers are to football and how difficult it is to answer a question that professional analysts and fans alike attempt to answer every year. Hopefully this project would also help you make smart Super Bowl bets with friends and family! 
