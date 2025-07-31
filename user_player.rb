require_relative 'player'

module TicTacToe
  class UserPlayer < Player
    
    def select_position!
      @game.print_board
      loop do
        print "Select your #{marker} position"
        selection = gets.to_i
        return selection if @game.free_positions.include?(selection)
        puts "Position #{selection} is not available. Try again:"
      end
    end
    
    def to_s
      "User"
    end
  end
end