package main;

import java.awt.event.*;

import javax.swing.AbstractAction;

public class KeyHandler {
    public boolean upArrowPressed, downArrowPressed, leftArrowPressed, rightArrowPressed, enterPressed, escapePressed,
	jumpPressed, upPressed, downPressed, leftPressed, rightPressed,
	interactPressed, equip1Pressed, equip2Pressed, equip3Pressed, equip4Pressed, equip5Pressed,
	anyKeyPressed;
	public String lastKeyPressed;

	//Menu Interaction
    public class UpArrowPressedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            upArrowPressed = true;
		}		
	}
    public class UpArrowReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            upArrowPressed = false;
		}		
	}
	public class DownArrowPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            downArrowPressed = true;
		}		
	}
    public class DownArrowReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            downArrowPressed = false;
		}		
	}
    public class LeftArrowPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            leftArrowPressed = true;
		}		
	}
    public class LeftArrowReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            leftArrowPressed = false;
		}		
	}
    public class RightArrowPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            rightArrowPressed = true;
		}		
	}
    public class RightArrowReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            rightArrowPressed = false;
		}		
	}
    public class EnterPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            enterPressed = true;
		}		
	}
    public class EnterReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            enterPressed = false;
		}		
	}
    public class EscapePressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            escapePressed = true;
		}		
	}
    public class EscapeReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            escapePressed = false;
		}		
	}

	//Movement
	public class JumpPressedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            jumpPressed = true;
		}		
	}
    public class JumpReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            jumpPressed = false;
		}		
	}
	public class UpPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            upPressed = true;
		}		
	}
    public class UpReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            upPressed = false;
		}		
	}
	public class DownPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            downPressed = true;
		}		
	}
    public class DownReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            downPressed = false;
		}		
	}
    public class LeftPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            leftPressed = true;
		}		
	}
    public class LeftReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            leftPressed = false;
		}		
	}
    public class RightPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            rightPressed = true;
		}		
	}
    public class RightReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            rightPressed = false;
		}		
	}
	
	//Misc
	public class InteractPressedAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            interactPressed = true;
		}		
	}
    public class InteractReleasedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            interactPressed = false;
		}		
	}
	public class equip1PressedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            equip1Pressed = true;
			equip2Pressed = false;
			equip3Pressed = false;
			equip4Pressed = false;
			equip5Pressed = false;
			System.out.println("1 PRESSED");
		}		
	}
	public class equip2PressedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            equip1Pressed = false;
			equip2Pressed = true;
			equip3Pressed = false;
			equip4Pressed = false;
			equip5Pressed = false;
			System.out.println("2 PRESSED");
		}		
	}
	public class equip3PressedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            equip1Pressed = false;
			equip2Pressed = false;
			equip3Pressed = true;
			equip4Pressed = false;
			equip5Pressed = false;
			System.out.println("3 PRESSED");
		}		
	}
	public class equip4PressedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            equip1Pressed = false;
			equip2Pressed = false;
			equip3Pressed = false;
			equip4Pressed = true;
			equip5Pressed = false;
			System.out.println("4 PRESSED");
		}		
	}
	public class equip5PressedAction extends AbstractAction{
		@Override public void actionPerformed(ActionEvent e) {
            equip1Pressed = false;
			equip2Pressed = false;
			equip3Pressed = false;
			equip4Pressed = false;
			equip5Pressed = true;
			System.out.println("5 PRESSED");
		}		
	}

	//Used for setting keybindings
	public class QAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "Q";
		}
	}
	public class WAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "W";
		}
	}
	public class EAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "E";
		}
	}
	public class RAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "R";
		}
	}
	public class TAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "T";
		}
	}
	public class YAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "Y";
		}
	}
	public class UAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "U";
		}
	}
	public class IAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "I";
		}
	}
	public class OAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "O";
		}
	}
	public class PAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "P";
		}
	}
	public class AAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "A";
		}
	}
	public class SAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "S";
		}
	}
	public class DAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "D";
		}
	}
	public class FAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "F";
		}
	}
	public class GAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "G";
		}
	}
	public class HAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "H";
		}
	}
	public class JAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "J";
		}
	}
	public class KAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "K";
		}
	}
	public class LAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "L";
		}
	}
	public class ZAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "Z";
		}
	}
	public class XAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "X";
		}
	}
	public class CAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "C";
		}
	}
	public class VAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "V";
		}
	}
	public class BAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "B";
		}
	}
	public class NAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "N";
		}
	}
	public class MAction extends AbstractAction{
        @Override public void actionPerformed(ActionEvent e) {
            anyKeyPressed = true;
			lastKeyPressed = "M";
		}
	}
}	
