/** This class extends the textview and uses the NonUnderlinedClickableSpan class to take an array of words and 
 * make them tapable. It is important to note that this class uses a custom XML in order to work properly.
 * @author Eliezer Rodriguez
 * @version 1.0 March 18, 2013
 */
package edu.taylor.cse.sbrandle.biblemem.v001.wordsearch;


import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class WordSearchTextView  extends TextView{
	public String wordsArray[];
	public int sel =-3;
	public Context pcontext;
	public String question;
	public WordSearchSharedClass sharedInfo;
	
	public WordSearchTextView(Context context) {
		super(context);
		pcontext = context;
		question ="";


	}


	public WordSearchTextView(Context context, AttributeSet attrs) {

		super( context, attrs );
	}

	public WordSearchTextView(Context context, AttributeSet attrs, int defStyle) {

		super( context, attrs, defStyle );
	}

	/**
	 * This view is called every time a word is tapped in worder to update the currently selected 
	 * word.
	 * @param anArray
	 * @param context
	 */
	public void initializeTextView( String anArray[], Context context){
		clearAllText();
		wordsArray =anArray;
		pcontext = context;
		SpannableStringBuilder clickableVerse = calculateClickableSPans(wordsArray);
		this.setMovementMethod(LinkMovementMethod.getInstance());
		this.setText(clickableVerse, BufferType.SPANNABLE);
		this.setLinkTextColor( Color.BLUE );
		question ="";
		sharedInfo = new WordSearchSharedClass(context);
	}
	
	

	public void clearAllText(){
		this.setText("");


	}

	/**
	 * This functions loops through the array of words and makes them tappable. 
	 * @param tokens this is an string array containing the words
	 * @return
	 */
	private SpannableStringBuilder calculateClickableSPans(final String tokens[]) {
		SpannableStringBuilder ssb = new SpannableStringBuilder(); 
		for (int i =0; i < tokens.length; i++){
			try {
				SpannableStringBuilder tag = new SpannableStringBuilder(tokens[i]);

				tag.setSpan(new WordSearchNonUnderlinedClickableSpan(i, tokens[i], sel) {
					@Override
					public void onClick(View widget) {

						sel = this.getSpanTextID();
						initializeTextView( wordsArray.clone(),pcontext);
						sel = -1;
						
						sharedInfo.setCurrentlySelectecWord(wordsArray[this.getSpanTextID()]);
						
						Toast.makeText(pcontext, wordsArray[this.getSpanTextID()],
								Toast.LENGTH_SHORT).show();
					}
				}, 0, tokens[i].length(), 0);

				ssb.append(tag);
				ssb.append("  ");
			} catch (IndexOutOfBoundsException e) {}
		}

		return ssb;
	}

}
