# MyTeamsDemo

Q13:   Find all string combinations consisting only of 0, 1 and ?Find all string combinations consisting only of 0, 1 and ?
Add to PDF   code Mid   
Details
The input will be a string consisting only of the characters 0, 1 and ?, where the ? acts as a wildcard that can be either a 0 or 1, and your goal is to print all possible combinations of the string. For example, if the string is "011?0" then your program should output a set of all strings, which are: ["01100", "01110"].

Solution
The general algorithm we will write a solution for is:

Call the function with the string and an empty set where we begin pushing 0 and 1's.
Once we reach a ? make a copy of each string set, and for half append a 0 and for the other half append a 1.
Recursively call the function with a smaller string until the string is empty.
function patterns(str, all) {

  // if the string is empty, return all the string sets
  if (str.length === 0) { return all; }

  // if character is 0 or 1 then add the character to each
  // string set we currently have so far
  if (str[0] === '0' || str[0] === '1') {
    for (var i = 0; i < all.length; i++) {
      all[i].push(str[0]);  
    }
  }

  // for a wildcard, we make a copy of each string set
  // and for half of them we append a 0 to the string 
  // and for the other half we append a 1 to the string
  if (str[0] === '?') {
    var len = all.length;
    for (var i = 0; i < len; i++) {
      var temp = all[i].slice(0);
      all.push(temp);
    }
    for (var i = 0; i < all.length; i++) {
      (i < all.length/2) ? all[i].push('0') : all[i].push('1');  
    }
  }

  // recursively calculate all string sets
  return patterns(str.substring(1), all);

}

patterns('10?1?', [[]]);

--================

Given a source word, target word and an English dictionary, transform the source word to target by changing/adding/removing 1 character at a time, while all intermediate words being valid English words. Return the transformation chain which has the smallest number of intermediate words.

// dictionary, source, target
transformWord(['cat', 'bat', 'bet', 'bed', 'at', 'ad', 'ed'], 'cat', 'bed');
Solution
module.exports = function (dictionary, start, end) {
  // Create a function that will return an object which represents a graph
  // structure.
  var createGraph = function (dictionary) {
    var graph = {};

    // Create a simple helper function that will return a boolean whether the
    // words are one character apart
    var isOneCharDifference = function (word1, word2) {
      // If the second word is larger than the first word, reverse the
      // arguments and run again.
      if (word2.length > word1.length) {
        return isOneCharDifference(word2, word1);
      }

      // If the difference in length between the words is greater than one,
      // or the words are identical anyway, it's impossible.
      if (word1 === word2 || word2.length < word1.length - 1) {
        return false;
      }

      for (var i = 0; i < word1.length; i++) {
        // First we check whether replacing the character with the equivelent
        // from the second word with make the second word.
        if (word1.substr(0, i) + word2[i] + word1.substr(i + 1) === word2) {
          return true;
        }

        // Next we check if removing a single letter from the first word will
        // result in the second word.
        if (word1.substr(0, i) + word1.substr(i + 1) === word2) {
          return true;
        }
      }

      return false;
    };

    dictionary.forEach(function (word) {
      // Add the word to the graph structure with an array for the connecting
      // nodes.
      graph[word] = [];

      // Check all the other words in the graph so far and see if they are
      // connections.
      Object.keys(graph).forEach(function (connection) {
        if (isOneCharDifference(word, connection)) {
          graph[word].push(connection);
          // Push the word into the connection if it's been created.
          graph[connection] && graph[connection].push(word);
        }
      });
    });

    return graph;
  };

  // Find the solution.
  var graph = createGraph(dictionary);
  var shortestRoute;

  (function findRoute (word, route) {
    // If the word doesn't exist in the graph or we have gone to the word
    // before, break early.
    if (!graph[word] || ~route.indexOf(word)) { return; }

    // If the route is longer than a previous route, stop trying to loop around.
    if (shortestRoute && route.length >= shortestRoute.length) { return; }

    // Push the word into the current route
    route.push(word);

    // If the word now matches the final word, set it as the route.
    if (word === end) {
      return shortestRoute = route;
    }

    graph[word].forEach(function (connection) {
      return findRoute(connection, route.slice());
    });
  })(start, []);

  return shortestRoute;
};
