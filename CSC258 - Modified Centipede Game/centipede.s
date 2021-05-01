##################################################################### 
# 
# CSC258H Winter 2021 Assembly Final Project
# University of Toronto, St. George 
# 
# Student: Lance Uy, 1006123570
# 
# Bitmap Display Configuration: 
# - Unit width in pixels: 8    
# - Unit height in pixels: 8 
# - Display width in pixels: 256 
# - Display height in pixels: 256 
# - Base Address for Display: 0x10008000 ($gp) 
# 
# Which milestone is reached in this submission? 
# Milestones 1, 2, and 3
# 
# Which approved additional features have been implemented?
# None
# 
# Any additional information that the TA needs to know: 
# - Once compiled or if you choose to play again, press "s" to begin the game
# - Use "j" and "k" to move left and right respectively
# - Use "x" to fire a bullet
#	- Note that you can only fire 1 bullet at a time to avoid spam
#	 (you can fire again if the bullet hits a mushroom, centipede, flea 
#	  or goes outside)
# - Mushrooms (Brown) and Fleas (Yellow) will disappear when come in contact with a bullet.
# - You will win if you hit the centipede 3 times
# - You will lose if either the Flea or Centipede hits you
# 	- In both cases, a prompt will appear asking you whether you want to restart or not
#
#####################################################################

.data
	displayAddress:	.word 0x10008000
	bugLocation: .word 1008
	centipedeX: .word 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
	centipedeY: .word 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
	centipedeDirection: .word 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
	centiHealth: .byte 3
			
	screenHeight: .byte 32
	screenWidth: .byte 32
	screenWidth1: .word 32
	player_x: 			.byte 16
	player_shot: 		.word 0 	# Address in memory of the Shooting position
	move_return_shot: 	.byte 0 	# 0 = does not exist, 1 = Shooting exists
	player_shot_move:  	.byte 0 	# Counter used for speed. Do not change
	player_shot_speed: 	.byte 1
	
	flea_shot: 		.word 0 	# Address in memory of the Shooting position
	flea_return_shot: 	.byte 0 	# 0 = does not exist, 1 = Shooting exists
	flea_shot_move:  	.byte 0 	# Counter used for speed. Do not change
	flea_shot_speed: 	.byte 3
	
	# Colours
	centiColor: 	.word	0xff0000	 # red
	centiHead:	.word	0x00ff00	 # green
	mushroom:    .word	0xD2691E	 # brown	
	shooter: 	.word	0xffffff	 # white
	black: 		.word	0x000000 	 # black
	blue: 		.word 	0x00ffff	 # blue
	flea: 		.word	0xffff00	 # yellow
		
	lostMessage:	.asciiz "You have died. "
	byebye:	.asciiz  "RIP."
	wonMessage:	.asciiz "You have won! "
	yay:	.asciiz  "YAY!"
	replayMessage:	.asciiz "Would you like to replay?"
.text
main:
######################################################
# Fill Screen to Black, for reset
######################################################
	lw $a0, screenWidth1
	lw $a1, black
	mul $a2, $a0, $a0 #total number of pixels on screen
	mul $a2, $a2, 4 #align addresses
	add $a2, $a2, $gp #add base of gp
	add $a0, $gp, $zero #loop counter
	
FillLoop:
	beq $a0, $a2, Init
	sw $a1, 0($a0) #store color
	addiu $a0, $a0, 4 #increment counter
	j FillLoop

Init:
	li $t0, 16
	sb $t0, player_x
	li $t0, 0
	sw $t0, player_shot
	sw $t0, flea_shot
	sb $t0, player_shot_move
	sb $t0, move_return_shot
	sb $t0, flea_shot_move
	sb $t0, flea_return_shot
	
	li $t0, 1
	sb $t0, player_shot_speed

	li $t0, 1008
	sw $t0, bugLocation
	
	li $t0, 3
	sb $t0, centiHealth
	sb $t0, flea_shot_speed
	
	addi $a3, $zero, 10	 # load a3 with the loop count (10)
	la $a0, centipedeX # load the address of the array into $a1
	la $a1, centipedeY
	la $a2, centipedeDirection # load the address of the array into $a2
	loop1:
		li $t0, 0
		sw $t0, 0($a1)
		addi $a1, $a1, 4
		
		li $t0, 1
		sw $t0, 0($a2)
		addi $a2, $a2, 4
		addi $a3, $a3, -1
		bne $a3, $zero, loop1
	li $t0, 0
	sw $t0, 0($a0)
	addi $a0, $a0, 4
	li $t0, 1
	sw $t0, 0($a0)
	addi $a0, $a0, 4
	li $t0, 2
	sw $t0, 0($a0)
	addi $a0, $a0, 4
	li $t0, 3
	sw $t0, 0($a0)
	addi $a0, $a0, 4
	li $t0, 4
	sw $t0, 0($a0)
	addi $a0, $a0, 4
	li $t0, 5
	sw $t0, 0($a0)
	addi $a0, $a0, 4
	li $t0, 6
	sw $t0, 0($a0)
	addi $a0, $a0, 4
	li $t0, 7
	sw $t0, 0($a0)
	addi $a0, $a0, 4
	li $t0, 8
	sw $t0, 0($a0)
	addi $a0, $a0, 4
	li $t0, 9
	sw $t0, 0($a0)
	addi $a0, $a0, 4
	
ClearRegisters:
	li $v0, 0
	li $a0, 0
	li $a1, 0
	li $a2, 0
	li $a3, 0
	li $t0, 0
	li $t1, 0
	li $t2, 0
	li $t3, 0
	li $t4, 0
	li $t5, 0
	li $t6, 0
	li $t7, 0
	li $t8, 0
	li $t9, 0
	li $s0, 0
	li $s1, 0
	li $s2, 0
	li $s3, 0
	li $s4, 0	

mushrooms:
	lw $t0, displayAddress  # $t0 stores the base address for display
	lw $t3, mushroom  # $t3 stores the mushroom colour code
	addi $t5, $zero, 4
	addi $a3, $zero, 20
	arr_loop3:
		jal get_random_number
		mult $a0, $t5
		mflo $t2
		addu $t6, $t0, $t2
		sw $t3, ($t6) # paint random unit in $t6 as a mushroom
		addi $a3, $a3, -1 
		bne $a3, $zero, arr_loop3
		
disp_centiped:
	addi $a3, $zero, 10	 # load a3 with the loop count (10)
	la $a1, centipedeX # load the address of the array into $a1
	la $a2, centipedeDirection # load the address of the array into $a2

arr_loop:	#iterate over the loops elements to draw each body in the centiped
	lw $t1, 0($a1)		 # load a word from the centipedLocation array into $t1
	lw $t5, 0($a2)		 # load a word from the centipedDirection  array into $t5
	#####
	lw $t2, displayAddress  # $t2 stores the base address for display
	lw $t3, centiColor	# $t3 stores the red colour code
	lw $t8, centiHead
	lw $t7, black
	
	sll $t4,$t1, 2		# $t4 is the bias of the old body location in memory (offset*4)
	add $t4, $t2, $t4	# $t4 is the address of the old bug location
	sw $t3, 0($t4)		# paint the body with red
	
	addi $a1, $a1, 4	 # increment $a1 by one, to point to the next element in the array
	addi $a2, $a2, 4
	addi $a3, $a3, -1	 # decrement $a3 by 1

	bne $a3, $zero, arr_loop
	sw $t8, 0($t4)

disp_shooter:
	lw $t0, displayAddress  # $t0 stores the base address for display
	lw $t3, shooter  # $t3 stores the mushroom colour code
	addi $t5, $zero, 4
	mul $t5, $t5, 1008
	#mult $a0, $t5
	#mflo $t2
	addu $t6, $t0, $t5
	sw $t3, ($t6)

load_flea:
	jal Shooting_flea
	jal show_shot_flea
	
start_loop:
	jal check_keystroke
	j start_loop

Main_loop:
	jal move_shot
	jal move_shot
	jal move_shot_flea
	jal reset_centipede
	jal update_centipede
	jal check_keystroke
	#jal respond_to_x
	li $v0, 32				# Sleep op code
	li $a0, 50				# Sleep 1/20 second 
	syscall

	j Main_loop	

Main_trigger:
	jal Shooting
	jal show_shot
	j Main_loop
	
Flea_trigger:
	jal Shooting_flea
	jal show_shot_flea
	j Main_loop

Exit:
	li $v0, 59 #syscall value for dialog
	la $a0, lostMessage #get message
	la $a1, byebye	#get score
	syscall
	
	li $v0, 50 #syscall for yes/no dialog
	la $a0, replayMessage #get message
	syscall
	
	beqz $a0, main#jump back to start of program
	#end program
	li $v0, 10
	syscall
	
Exit_win:
	li $v0, 59 #syscall value for dialog
	la $a0, wonMessage #get message
	la $a1, yay	#get score
	syscall
	
	li $v0, 50 #syscall for yes/no dialog
	la $a0, replayMessage #get message
	syscall
	
	beqz $a0, main#jump back to start of program
	#end program
	li $v0, 10
	syscall

####################################################
# Returns the address to save the pixel in the $gp
# $a0 = x, $a1 = y
# Returns $v0
AddressInMemory:
	lbu $v0 screenWidth
	mulu $a1 $a1 $v0
	addu $v0 $a0 $a1
	sll $v0 $v0 2
	addu $v0 $v0 $gp
	jr $ra
####################################################
# Create Player Generated Shooting
Shooting:sw $ra ($sp)
	lb $t2 move_return_shot
	bnez $t2 Shooting_return
	lb $a0 player_x
	li $a1 30
	jal AddressInMemory
	sw $v0 player_shot
	li $t1 1
	sb $t1 move_return_shot
Shooting_return:
	lw $ra ($sp)
	jr $ra
	
####################################################
# Create Player Generated Shooting
Shooting_flea:sw $ra ($sp)
	lb $t2 flea_return_shot
	bnez $t2 Shooting_return_flea
	#lb $a0 player_x
	#li $a0 31
	jal get_random_number_flea
	li $a1 0
	jal AddressInMemory
	sw $v0 flea_shot
	li $t1 1
	sb $t1 flea_return_shot
	
Shooting_return_flea:
	lw $ra ($sp)
	jr $ra
	
####################################################
# Move Player Shooting Up
move_shot:
	sw $ra ($sp)
	lb $t2 move_return_shot # 0 
	beqz $t2 move_shot_return # if move return shot = 0
	lb $t1 player_shot_move
	lb $t2 player_shot_speed
	addi $t1 $t1 1
	sb $t1 player_shot_move
	bne $t1 $t2 move_shot_return # so that the Shooting moves slower
	jal clean_shot
	jal end_shot
	li $t1 0
	sb $t1 player_shot_move
	lw $t0 player_shot
	subi $t0 $t0 128
	sw $t0 player_shot
	jal show_shot
move_shot_return:
	lw $ra ($sp)
	jr $ra
####################################################
# Move Flea Down
move_shot_flea:
	sw $ra ($sp)
	lw $t4, mushroom
	
	lb $t2 flea_return_shot # 0 
	beqz $t2 Flea_trigger #move_shot_return_flea # if move return shot = 0
	lb $t1 flea_shot_move
	lb $t2 flea_shot_speed
	addi $t1 $t1 1
	sb $t1 flea_shot_move
	bne $t1 $t2 move_shot_return_flea # so that the Shooting moves slower
	
	jal clean_shot_flea
	jal end_shot_flea
	li $t1 0
	sb $t1 flea_shot_move
	lw $t0 flea_shot
	addi $t0 $t0 128
	sw $t0 flea_shot
	
	lw $t0 flea_shot
	move $t1 $t0
	#addi $t1 $t1 128
	lw $t3 ($t1)
	
	beq $t3, $t4, move_shot_return_flea
	jal show_shot_flea
	#jal clean_shot

move_shot_return_flea:
	lw $ra ($sp)
	jr $ra
	
####################################################
# Paint behind black
clean_shot:
	lw $t0 player_shot
	lw $t1 black
	sw $t1 0($t0)
	jr $ra
	
####################################################
# Paint black or mushroom depending on what is ahead
clean_shot_flea:
	lw $t0 flea_shot
	move $t1 $t0
	lw $t3 ($t1)
	beq $t3, $t4, equals
	bne $t3, $t4, else

equals:
	lw $t0 flea_shot
	lw $t1 mushroom
	sw $t1 0($t0)
	jr $ra
else:
	lw $t0 flea_shot
	lw $t1 black
	sw $t1 0($t0)
	jr $ra
	
####################################################
# Shows Flea Dropping
show_shot_flea:
	lw $t0 flea_shot
	lw $t1 flea
	sw $t1 0($t0)
	jr $ra
	
####################################################
# Shows Player Shooting
show_shot:
	lw $t0 player_shot
	lw $t1 blue
	sw $t1 0($t0)
	jr $ra
	
####################################################
# End the shot of the player
end_shot:
	lw $t0 player_shot
	move $t1 $t0
	subi $t1 $t1 128
	
	ble $t1 0x10008000 end_shot_done_and_clean # Hits top border
	lw $t2 ($t1)
	lw $t3 black

	lw $t4, mushroom
	lw $t5, centiColor
	lw $t6, centiHead
	lw $t7, flea
	
	beq $t4 $t2 end_shot_done_and_clean
	beq $t5 $t2 end_shot_done_and_clean_centi
	beq $t6 $t2 end_shot_done_and_clean_centi
	beq $t7 $t2 end_shot_done_and_flea

	b end_shot_return

end_shot_done:
	li $t0 0
	sb $t0 move_return_shot
	sb $t0 player_shot_move

end_shot_done_and_flea:
	li $t0 0
	sb $t0 move_return_shot
	sb $t0 player_shot_move
	sb $t0 flea_return_shot
	sb $t0 flea_shot_move

	lw $t0 player_shot
	subi $t0 $t0 128
	sw $t0 player_shot
	jal clean_shot
	
	lw $t0 flea_shot
	addi $t0 $t0 128
	sw $t0 flea_shot
	jal clean_shot_flea
	#jal clean_shot_flea
	j move_shot_return
	
end_shot_done_and_clean:
	li $t0 0
	sb $t0 move_return_shot
	sb $t0 player_shot_move
	
	li $t1 0
	sb $t1 player_shot_move
	lw $t0 player_shot
	subi $t0 $t0 128
	sw $t0 player_shot
	jal clean_shot
	j move_shot_return

end_shot_done_and_clean_centi:
	li $t0 0
	sb $t0 move_return_shot
	sb $t0 player_shot_move
	
	li $t1 0
	sb $t1 player_shot_move
	lw $t0 player_shot
	subi $t0 $t0 128
	sw $t0 player_shot
	jal clean_shot
	
	lb $t2, centiHealth
	add $t2, $t2, -1
	sb $t2, centiHealth
	beqz $t2, Exit_win
	j move_shot_return
	#sw $t3 ($t1)
	#b clean_shot
end_shot_return:
	jr $ra
####################################################
# End flea movement and delete
end_shot_flea:
	lw $t0 flea_shot
	move $t1 $t0
	addi $t1 $t1 128
	
	bge $t1 0x10009000 end_shot_done_flea # llego a la primera fila x = 0
	lw $t2 ($t1)
	lw $t3 black
	lw $t7, blue
	lw $t8, shooter

	#beq $t4 $t2 clean_shot
	#sw $t3 ($t1)
	beq $t7, $t2, end_shot_done_flea_and_shooter
	beq $t8, $t2, Exit

	b end_shot_return_flea

end_shot_done_flea:
	li $t0 0
	sb $t0 flea_return_shot
	sb $t0 flea_shot_move
	j move_shot_return_flea
end_shot_done_flea_and_shooter:
	li $t0 0
	sb $t0 move_return_shot
	sb $t0 player_shot_move
	sb $t0 flea_return_shot
	sb $t0 flea_shot_move
	
	lw $t0 flea_shot
	addi $t0 $t0 128
	sw $t0 flea_shot
	jal clean_shot_flea
	
	lw $t0 player_shot
	subi $t0 $t0 128
	sw $t0 player_shot
	jal clean_shot
	j move_shot_return_flea
	
end_shot_return_flea:
	jr $ra

# function to update centipede
update_centipede:
	addi $sp, $sp, -4
	sw $ra, 0($sp)

	addi $a3, $zero, 10	 # load a3 with the loop count (10)
	la $a0, centipedeX # load the address of the array into $a1
	la $a1, centipedeY # load the address of the array into $a1
	la $a2, centipedeDirection # load the address of the array into $a2
	
segment_loop:
	lw $t0, 0($a0)		# x coor
	lw $t1, 0($a1)		# y coor
	lw $t2, 0($a2)
	
	bgtz $t2, check_right
	beqz $t2, check_left
	
		check_right:
			addi $t3, $zero, 31
			lw $t5, mushroom
			lw $t8, shooter
			
			addi $t7, $t0, 1			# Find the address of pixel
			sll $t4, $t1, 5				# idx = doodle.y * 32
			add $t4, $t4, $t7			# idx = (doodle.y * 32) + x
			sll $t4, $t4, 2				# idx = (doodle.y * 32 + x) * 4
			add $t4, $t4, $gp
			
			lw $t6, 0($t4)		# Get colour of pixel
			beq $t6, $t8, Exit 
			beq $t6, $t5, turn_left #If colors are equal - turn left
			
				
			
			beq $t0, $t3, turn_left	
			addi $t0, $t0, 1
			sw $t0, 0($a0)
			j paint_centi
			
		check_left:
			addi $t3, $zero, 0
			lw $t5, mushroom
			lw $t8, shooter
			
			addi $t7, $t0, -1			# Find the address of pixel
			sll $t4, $t1, 5				# idx = doodle.y * 32
			add $t4, $t4, $t7			# idx = (doodle.y * 32) + x
			sll $t4, $t4, 2				# idx = (doodle.y * 32 + x) * 4
			add $t4, $t4, $gp

			lw $t6, 0($t4)		# Get colour of pixel
			
			beq $t6, $t8, Exit 
			beq $t6, $t5, turn_right #If colors are equal - turn left
			
			beq $t0, $t3, turn_right
			addi $t0, $t0, -1
			sw $t0, 0($a0)
			j paint_centi
			
	turn_right:
		addi $t4, $t1, 1
		sw $t4, 0($a1)
		addi $t4, $zero, 1
		sw $t4, 0($a2)
		j paint_centi
		
	turn_left:
		addi $t4, $t1, 1
		sw $t4, 0($a1)
		addi $t4, $zero, 0
		sw $t4, 0($a2)
		j paint_centi
	
	paint_centi:
		jal paint_part
		addi $a0, $a0, 4	 # increment $a1 by one, to point to the next element in the array
		addi $a1, $a1, 4
		addi $a2, $a2, 4
		addi $a3, $a3, -1	 # decrement $a3 by 1
		#jal paint_head #figure out how to get head
		bne $a3, $zero, segment_loop
		
		lw $t5, centiHead
		sw $t5, 0($t4)
		
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra
	
paint_part:
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	lw $t1, 0($a1)
	lw $t0, 0($a0)
	
	sll $t4, $t1, 5				# idx = doodle.y * 32
	add $t4, $t4, $t0			# idx = (doodle.y * 32) + x
	sll $t4, $t4, 2				# idx = (doodle.y * 32 + x) * 4
	add $t4, $t4, $gp
	
	lw $t5, centiColor

	sw $t5, 0($t4)		# paint the body with red
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra

reset_centipede:
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $a3, $zero, 10	 # load a3 with the loop count (10)
	la $a0, centipedeX # load the address of the array into $a1
	la $a1, centipedeY # load the address of the array into $a1
	la $a2, centipedeDirection # load the address of the array into $a2

arr_loop2:	#iterate over the loops elements to draw each body in the centiped
	lw $t1, 0($a0)		 # load a word from the centipedeX array into $t1
	lw $t2, 0($a1)		 # load a word from the centipedeY  array into $t5
	#####
	
	sll $t0, $t2, 5				# idx = doodle.y * 32
	add $t0, $t0, $t1			# idx = (doodle.y * 32) + x
	sll $t0, $t0, 2				# idx = (doodle.y * 32 + x) * 4
	add $t0, $t0, $gp
	
	lw $t4, black
	
	sw $t4, 0($t0)		# paint the ending with black
	
	addi $a0, $a0, 4	 # increment $a1 by one, to point to the next element in the array
	addi $a1, $a1, 4
	addi $a3, $a3, -1	 # decrement $a3 by 1
	bne $a3, $zero, arr_loop2
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra
	
get_random_number:
  	li $v0, 42         # Service 42, random int bounded
  	li $a0, 0          # Select random generator 0
  	li $a1, 800 	#128 * 31 rows / 4 bytes            
  	syscall             # Generate random int (returns in $a0)
  	jr $ra

get_random_number_flea:
  	li $v0, 42         # Service 42, random int bounded
  	li $a0, 0          # Select random generator 0
  	li $a1, 32 	#128 * 31 rows / 4 bytes            
  	syscall             # Generate random int (returns in $a0)
  	jr $ra
	
# function to detect any keystroke
check_keystroke:
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	lw $t8, 0xffff0000
	beq $t8, 1, get_keyboard_input # if key is pressed, jump to get this key
	addi $t8, $zero, 0
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra
	
# function to get the input key
get_keyboard_input:
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	lw $t2, 0xffff0004
	addi $v0, $zero, 0	#default case
	beq $t2, 0x6A, respond_to_j
	beq $t2, 0x6B, respond_to_k
	beq $t2, 0x78, Main_trigger
	beq $t2, 0x73, respond_to_s
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra
	
# Call back function of j key
respond_to_j:
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	lb $a0, player_x
	
	la $t0, bugLocation	# load the address of buglocation from memory
	lw $t1, 0($t0)		# load the bug location itself in t1
	
	lw $t2, displayAddress  # $t2 stores the base address for display
	lw $t3, black	# $t3 stores the black colour code
	
	sll $t4,$t1, 2		# $t4 the bias of the old buglocation
	add $t4, $t2, $t4	# $t4 is the address of the old bug location
	sw $t3, 0($t4)		# paint the first (top-left) unit white.
	
	beq $t1, 992, skip_movement # prevent the bug from getting out of the canvas
	addi $t1, $t1, -1	# move the bug one location to the right
	add $a0, $a0, -1
	sb $a0, player_x
skip_movement:
	sw $t1, 0($t0)		# save the bug location

	lw $t3, shooter	# $t3 stores the white colour code
	
	sll $t4,$t1, 2
	add $t4, $t2, $t4
	sw $t3, 0($t4)		# paint the first (top-left) unit white.
	
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra

# Call back function of k key
respond_to_k:
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	lb $a0, player_x
	
	la $t0, bugLocation	# load the address of buglocation from memory
	lw $t1, 0($t0)		# load the bug location itself in t1
	
	lw $t2, displayAddress  # $t2 stores the base address for display
	lw $t3, black	# $t3 stores the black colour code
	
	sll $t4,$t1, 2		# $t4 the bias of the old buglocation
	add $t4, $t2, $t4	# $t4 is the address of the old bug location
	sw $t3, 0($t4)		# paint the block with black
	
	beq $t1, 1023, skip_movement2 #prevent the bug from getting out of the canvas
	addi $t1, $t1, 1	# move the bug one location to the right
	add $a0, $a0, 1
	sb $a0, player_x
skip_movement2:
	sw $t1, 0($t0)		# save the bug location

	lw $t3, shooter	# $t3 stores the white colour code
	
	sll $t4,$t1, 2
	add $t4, $t2, $t4
	sw $t3, 0($t4)		# paint the block with white
	
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra
	
	
respond_to_s:
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	j Main_loop
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra

delay:
	# move stack pointer a work and push ra onto it
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	li $a2, 10000
	addi $a2, $a2, -1
	bgtz $a2, delay
	
	# pop a word off the stack and move the stack pointer
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	jr $ra
