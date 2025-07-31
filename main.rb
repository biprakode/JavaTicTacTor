require_relative 'game'

include TicTacToe

Game.new(AIPlayer, AIPlayer).play
puts
players = [UserPlayer, AIPlayer].shuffle
Game.new(*players).play