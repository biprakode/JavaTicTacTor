module TicTacToe
  class Player
    attr_reader :marker

    def initialize(game , marker)
      @game = game
      @marker = marker
    end
  end
end