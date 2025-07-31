require_relative 'player'

module TicTacToe
  class AIPlayer < Player
    DEBUG = false
    LINES = [[1,2,3], [4,5,6], [7,8,9], [1,4,7], [2,5,8], [3,6,9], [1,5,9], [3,5,7]]

    def initialize(game, marker)
      super(game, marker)
    end

    def select_position!
      opponent_marker = @game.opponent.marker
      winning_or_blocking_position = win_block_position_searcher(opponent_marker)
      puts "win_block_position_searcher: #{winning_or_blocking_position.inspect}" if DEBUG
      return winning_or_blocking_position if winning_or_blocking_position
      if corner_trap_defense_needed?
        position = corner_trap_defense_position(opponent_marker)
        puts "corner_trap_defense_position: #{position.inspect}" if DEBUG
        return position
      end
      position = random_prioritized_position
      puts "random_prioritized_position: #{position.inspect}" if DEBUG
      position
    end

    def group_positions_by_markers(line)
      markers = line.group_by { |position| @game.board[position] }
      markers.default = []
      markers
    end

    def win_block_position_searcher(opponent_marker)
      blocking_position = nil
      for line in LINES
        markers = group_positions_by_markers(line)
        next if markers[nil].length != 1
        if markers[self.marker].length == 2
          log_debug "winning on line #{line.join}"
          return markers[nil].first
        elsif markers[opponent_marker].length == 2
          log_debug "could block on line #{line.join}"
          blocking_position = markers[nil].first
        end
      end
      if blocking_position
        log_debug "blocking at #{blocking_position}"
        return blocking_position
      end
      nil
    end

    def corner_trap_defense_needed?
      corner_positions = [1, 3, 7, 9]
      opponent_chose_a_corner = corner_positions.any? { |pos| @game.board[pos] != nil }
      @game.turn_num == 2 && opponent_chose_a_corner
    end

    def corner_trap_defense_position(opponent_marker)
      log_debug "defending against corner start by playing adjacent"
      opponent_position = @game.board.find_index { |marker| marker == opponent_marker }
      safe_responses = { 1 => [2, 4], 3 => [2, 6], 7 => [4, 8], 9 => [6, 8] }
      safe_responses[opponent_position].sample
    end

    def random_prioritized_position
      log_debug "picking random position, favoring center and then corners"
      ([5] + [1, 3, 7, 9].shuffle + [2, 4, 6, 8].shuffle).find do |pos|
        @game.free_positions.include?(pos)
      end
    end

    def log_debug(message)
      puts "#{self}: #{message}" if DEBUG
    end

    def to_s
      "Computer#{@game.current_player_id}"
    end
  end
end