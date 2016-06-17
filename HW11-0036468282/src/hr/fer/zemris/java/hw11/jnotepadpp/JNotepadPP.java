package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.function.UnaryOperator;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizationProvider;

/**
 * JNotepad++ is multi-tab text editing application. It provides some useful
 * operations on files (beside basic New, Open, Save, Save As, Cut, Copy,
 * Paste operations):
 * <p>
 * To Lower Case -> changes selected characters to lower case.</br>
 * To Upper Case -> changes selected characters to upper case.</br>
 * Toggle Case -> switches lower to upper and upper to lower characters.</br>
 * Ascending sort -> sorts lines ascending alphabetically.</br>
 * Descending sort -> sorts lines descending alphabetically.</br>
 * Unique lines -> Removes duplicate lines from file.</br>
 * </p>
 * At the bottom of main window, info bar provides informations about text:
 * character length, selected line and column number, length of selected text
 * and clock which also shows date.
 * 
 * @author Domagoj
 *
 */
public class JNotepadPP extends JFrame {

	/** Serialization version control number. */
	private static final long serialVersionUID = 1L;

	/** Container for all opened tabs in JNotepad++ */
	private JTabbedPane tabbedPane;

	/** JNotepad++ info-bar clock */
	private JNotepadClock clock;

	/** Map of all currently opened files */
	private Map<Component, Path> openedFilePaths;

	/** Localization provider for main JNotepad++ window frame */
	private FormLocalizationProvider flp = new FormLocalizationProvider(
			LocalizationProvider.getInstance(), this);

	/**
	 * Currently active language {@link Locale} instance. Used for sorting
	 * lines.
	 */
	private Locale activeLocale;

	/**
	 * Icon for not edited or saved document tabs. Icon is shown before tab
	 * name.
	 */
	private ImageIcon notEditedIcon;

	/** Icon for edited document tabs. Icon is shown before tab name. */
	private ImageIcon editedIcon;

	/**
	 * Label for character length of active tab. Shown in bottom left corner.
	 */
	private JLabel docLengthInfo;

	/**
	 * Label for number of line number of caret position in active tab. Shown in
	 * bottom info bar.
	 */
	private JLabel selLineInfo;

	/**
	 * Label for column number of caret position in active tab. Shown in bottom
	 * info bar.
	 */
	private JLabel selColInfo;

	/**
	 * Label for character length of selected part in active tab. Shown in
	 * bottom info bar.
	 */
	private JLabel selLengthInfo;

	/** Number of newly opened tabs. Initially set to 1. */
	private int numOfNewTabs = 1;
	
	/**
	 * Path from current class path to icon for not edited or saved document
	 * tabs.
	 */
	private final static String NOT_EDIT_ICON_PATH = "icons/disk-icon.png";

	/**
	 * Path from current class path to icon for edited or saved document tabs.
	 */
	private final static String EDIT_ICON_PATH = "icons/disk-icon-red.png";
	
	/** Font of text */
	private static final Font FONT = new Font(null, Font.PLAIN, 14);

	/**
	 * Creates new instance of {@link JNotepadPP}
	 */
	public JNotepadPP() {
		super();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(1024, 768);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignore) {
		}
		
		activeLocale = Locale.ENGLISH;
		flp.connect();
		initGUI();
	}

	/**
	 * Initializes {@link JNotepadPP} GUI.
	 */
	private void initGUI() {
		openedFilePaths = new HashMap<>();
		notEditedIcon = getIcon(NOT_EDIT_ICON_PATH);
		editedIcon = getIcon(EDIT_ICON_PATH);
		tabbedPane = new JTabbedPane();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});

		tabbedPane.addChangeListener((e) -> {
			if (openedFilePaths.get(tabbedPane.getSelectedComponent()) == null) {
				JNotepadPP.this.setTitle("JNotepad++");
			} else {
				JNotepadPP.this.setTitle(
						openedFilePaths.get(tabbedPane.getSelectedComponent()) + " - JNotepad++");
			}
		});

		addTab("new 1");
		numOfNewTabs++;

		createActions();
		createMenus();
		createToolbars();
		createInfoBar();
	}

	/**
	 * Reads icon from disk and returns it as instance of {@link ImageIcon}.
	 * 
	 * @param iconPath Path to icon
	 * @return instance of ImageIcon
	 */
	private ImageIcon getIcon(String iconPath) {
		try (InputStream is = this.getClass().getResourceAsStream(iconPath);) {
			byte[] bytes = JNotepadPPUtil.readAllBytes(is);
			is.close();
			return new ImageIcon(bytes);
		} catch (IOException | NullPointerException e) {
			JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("error.icon"),
					flp.getString("error.key"), JOptionPane.ERROR_MESSAGE);
			return new ImageIcon();
		}

	}

	/**
	 * Changes current language to English.
	 */
	private LocalizableAction engLangAction = new LocalizableAction("en", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
			activeLocale = Locale.ENGLISH;
		}
	};

	/**
	 * Changes current language to German.
	 */
	private LocalizableAction deLangAction = new LocalizableAction("de", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
			activeLocale = Locale.GERMAN;
		}
	};

	/**
	 * Changes current language to Croatian.
	 */
	private LocalizableAction hrLangAction = new LocalizableAction("hr", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
			activeLocale = Locale.forLanguageTag("hr");
		}
	};

	/**
	 * Adds new opened document called "new n". "n" is number of newly opened
	 * tabs.
	 */
	private LocalizableAction addEmptyDocumentAction = new LocalizableAction("new", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			addTab("new " + numOfNewTabs++);
		}
	};

	/**
	 * Opens document in new tab.
	 */
	private LocalizableAction openDocumentAction = new LocalizableAction("open", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open");
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File file = fc.getSelectedFile();
			Path filePath = file.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						String.format(flp.getString("open.notReadableError"),
								file.getAbsolutePath()),
						flp.getString("error"), JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] bytes;
			try {
				bytes = Files.readAllBytes(filePath);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("open.readingError"),
						flp.getString("error"), JOptionPane.ERROR_MESSAGE);
				return;
			}
			String tekst = new String(bytes, StandardCharsets.UTF_8);
			addTab(file.getName());
			((JTextArea) ((JScrollPane) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()))
					.getViewport().getComponent(0)).setText(tekst);
			tabbedPane.setIconAt(tabbedPane.getSelectedIndex(), notEditedIcon);
			openedFilePaths.put(tabbedPane.getSelectedComponent(), filePath);
			JNotepadPP.this.setTitle(filePath + " - JNotepad++");
			return;
		}
	};

	/**
	 * Adds new empty tab.
	 * 
	 * @param name Name of added tab.
	 */
	private void addTab(String name) {
		JTextArea editor = new JTextArea();
		editor.setFont(FONT);
		editor.getDocument().addDocumentListener(textChangeListener);
		editor.addCaretListener(caretListener);
		tabbedPane.addTab(name, notEditedIcon, new JPanel().add(new JScrollPane(editor)));
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
	}

	/**
	 * Modified {@link JFileChooser} for saving operation so that if file with
	 * given name already exists, user will be asked for confirmation - saving
	 * will overwrite old file.
	 */
	private JFileChooser saveFileChooser = new JFileChooser() {

		/** Serialization version control number. */
		private static final long serialVersionUID = 1L;

		@Override
		public void approveSelection() {
			if (this.getSelectedFile().exists()) {
				int decision = JOptionPane.showConfirmDialog(this, String
						.format(flp.getString("save.exists"), this.getSelectedFile().getName()));
				if (decision != JOptionPane.YES_OPTION) {
					return;
				}
			}
			super.approveSelection();
		};
	};

	/**
	 * Saves document to disk. Details of action are in {@link #saveDocument()}
	 * method.
	 */
	private LocalizableAction saveDocumentAction = new LocalizableAction("save", flp) {

		/**
		 * Serialization version control number.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveDocument();
		}
	};

	/**
	 * If file was opened from disk, it saves without request for confirmation
	 * from user. If file is newly made, it opens save dialog so that user would
	 * define file name and place where it'll be saved.
	 */
	private void saveDocument() {
		int option = JFileChooser.APPROVE_OPTION;
		if (openedFilePaths.get(tabbedPane.getSelectedComponent()) == null) {
			saveFileChooser.setDialogTitle("Save document");
			option = executeSaveDialog();
		}
		if (option == JFileChooser.APPROVE_OPTION) {
			Path filePath = openedFilePaths.get(tabbedPane.getSelectedComponent());
			writeFile(filePath);
		}
	}

	/**
	 * Opens save dialog where user defines name and place of saving file.
	 * Details of save dialog are in
	 * 
	 * @return
	 */
	private int executeSaveDialog() {
		int option = saveFileChooser.showSaveDialog(JNotepadPP.this);
		if (option != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("save.notSaved"),
					flp.getString("warning"), JOptionPane.WARNING_MESSAGE);
			return option;
		}
		Path filePath = saveFileChooser.getSelectedFile().toPath();
		openedFilePaths.put(tabbedPane.getSelectedComponent(), filePath);
		return option;
	}

	/**
	 * Saves document to disk. If file wasn't opened from disk, it saves without
	 * request for confirmation from user.
	 */
	private LocalizableAction saveAsDocumentAction = new LocalizableAction("saveAs", flp) {

		/**
		 * Serialization version control number.
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveFileChooser.setDialogTitle("Save As");
			if (executeSaveDialog() == JFileChooser.APPROVE_OPTION) {
				Path filePath = openedFilePaths.get(tabbedPane.getSelectedComponent());
				writeFile(filePath);
			}
		}
	};

	/**
	 * Writes byte data from active tab (text) and writes it to specified path.
	 * 
	 * @param filePath Writing file path.
	 */
	private void writeFile(Path filePath) {
		byte[] data = ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport()
				.getComponent(0)).getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(filePath, data);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("error.write"),
					flp.getString("error.key"), JOptionPane.ERROR_MESSAGE);
			return;
		}
		tabbedPane.setIconAt(tabbedPane.getSelectedIndex(), notEditedIcon);
		JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("save.conf"),
				flp.getString("notification"), JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Cuts selected text which is pasted with {@link #pasteAction} operation.
	 */
	private LocalizableAction cutAction = new LocalizableAction("cut", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea currentEditor = ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent())
					.getViewport().getComponent(0));
			currentEditor.cut();
		}
	};

	/**
	 * Copies selected text which is pasted with {@link #pasteAction} operation.
	 */
	private LocalizableAction copyAction = new LocalizableAction("copy", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea currentEditor = ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent())
					.getViewport().getComponent(0));
			currentEditor.copy();
		}
	};

	/**
	 * Pastes text to caret position.
	 */
	private LocalizableAction pasteAction = new LocalizableAction("paste", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea currentEditor = ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent())
					.getViewport().getComponent(0));
			currentEditor.paste();
		}
	};

	/**
	 * Changes casing of selected text - lower to upper and upper to lower case.
	 */
	private LocalizableAction toggleCaseAction = new LocalizableAction("toggleCase", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(JNotepadPPUtil.INVERT_CASE);
		}
	};

	/**
	 * Changes all characters in selected part of text to lower case.
	 */
	private LocalizableAction toLowerCaseAction = new LocalizableAction("toLowerCase", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(JNotepadPPUtil.To_LOWER_CASE);
		}
	};

	/**
	 * Changes all characters in selected part of text to upper case.
	 */
	private LocalizableAction toUpperCaseAction = new LocalizableAction("toUpperCase", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(JNotepadPPUtil.To_UPPER_CASE);
		}
	};

	/**
	 * Changes case of selected characters to case depending on operator
	 * argument.
	 * 
	 * @param operator String case change operator.
	 */
	private void changeCase(UnaryOperator<String> operator) {
		JTextArea currentEditor = ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent())
				.getViewport().getComponent(0));
		Document document = currentEditor.getDocument();
		int len = Math.abs(currentEditor.getCaret().getDot() - currentEditor.getCaret().getMark());
		int offset = 0;
		if (len != 0) {
			offset = Math.min(currentEditor.getCaret().getDot(),
					currentEditor.getCaret().getMark());
		} else {
			len = document.getLength();
		}
		try {
			String text = document.getText(offset, len);
			text = operator.apply(text);
			document.remove(offset, len);
			document.insertString(offset, text, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Sorts lines from active file alphabetically ascending.
	 */
	private LocalizableAction sortAscAction = new LocalizableAction("sortAsc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea currentEditor = ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent())
					.getViewport().getComponent(0));
			if (!currentEditor.getText().isEmpty()) {
				String sorted = JNotepadPPUtil.sort(currentEditor.getText(), activeLocale, false);
				currentEditor.setText(sorted);
			}
		}
	};

	/**
	 * Sorts lines from active file alphabetically descending.
	 */
	private LocalizableAction sortDescAction = new LocalizableAction("sortDesc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea currentEditor = ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent())
					.getViewport().getComponent(0));
			if (!currentEditor.getText().isEmpty()) {
				String sorted = JNotepadPPUtil.sort(currentEditor.getText(), activeLocale, true);
				currentEditor.setText(sorted);
			}
		}
	};

	/**
	 * Removes duplicate lines from active file.
	 */
	private LocalizableAction uniqueAction = new LocalizableAction("unique", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea currentEditor = ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent())
					.getViewport().getComponent(0));
			LinkedHashSet<String> set = new LinkedHashSet<>(
					Arrays.asList(currentEditor.getText().split("\n")));
			if (!currentEditor.getText().isEmpty()) {
				String unique = "";
				for (String line : set) {
					unique += line + "\n";
				}
				currentEditor.setText(unique);
			}
		}
	};

	/**
	 * Closes active tab. For details see {@link #close()}.
	 */
	private LocalizableAction closeAction = new LocalizableAction("close", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (tabbedPane.getComponentCount() > 1) {
				close();
			}
		}
	};

	/**
	 * Closes currently active tab. If document on tab was edited, it provides
	 * option to save changes.
	 * 
	 * @return Option made by user when asked to save changes or -1 if close
	 *         operation went without save dialog.
	 */
	private int close() {
		int option = -1;
		if (tabbedPane.getIconAt(tabbedPane.getSelectedIndex()) == editedIcon) {
			option = JOptionPane.showConfirmDialog(JNotepadPP.this,
					String.format(flp.getString("save.befClose"),
							tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())));
			if (option == JOptionPane.YES_OPTION) {
				saveFileChooser.setName("Save");
				int saveOption = executeSaveDialog();
				if (saveOption == JFileChooser.APPROVE_OPTION) {
					saveDocument();
				}
			} else if (option == JOptionPane.CANCEL_OPTION) {
				return option;
			}
		}

		openedFilePaths.remove(tabbedPane.getSelectedComponent());
		tabbedPane.remove(tabbedPane.getSelectedIndex());
		return option;
	}

	/**
	 * Shows current document text information: number of characters, non-empty
	 * characters and lines.
	 */
	private LocalizableAction infoAction = new LocalizableAction("info", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(JNotepadPP.this, String.format(flp.getString("info.text"),
					countCharacters(), countNonEmptyCharacters(), getNumOfLines()));
		}

	};

	/**
	 * Returns number of characters in active document tab.
	 * 
	 * @return number of characters in active document tab.
	 */
	private int countCharacters() {
		return ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport()
				.getComponent(0)).getText().length();
	}

	/**
	 * Returns number of non-empty characters in active document tab.
	 * 
	 * @return number of non-empty characters in active document tab.
	 */
	private int countNonEmptyCharacters() {
		return ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport()
				.getComponent(0)).getText().replaceAll("\\s+", "").length();
	}

	/**
	 * Returns number of lines.
	 * 
	 * @return number of lines.
	 */
	private int getNumOfLines() {
		return ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport()
				.getComponent(0)).getLineCount();
	}

	/**
	 * Exits JNotepad++ application. See {@link #exit()} for details.
	 */
	private LocalizableAction exitAction = new LocalizableAction("exit", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			exit();
		}
	};

	/**
	 * Exits application, but before it terminates, asks user for every edited
	 * tab if he/she wants to save changes. If user chooses "Cancel" instead
	 * "Yes" or "No", exiting is aborted.
	 */
	private void exit() {
		int option = -1;
		for (int i = tabbedPane.getTabCount() - 1; i >= 0; i--) {
			tabbedPane.setSelectedIndex(i);
			option = close();
			if (option == JOptionPane.CANCEL_OPTION) {
				break;
			}
		}
		if (option != JOptionPane.CANCEL_OPTION) {
			clock.stop();
			dispose();
		}
	}

	/**
	 * Listener of all text changes. At every text change, info-bar is updated:
	 * character number, column number and row number of caret position.
	 */
	private DocumentListener textChangeListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			noteDocChange();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			noteDocChange();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			noteDocChange();
		}
	};

	/**
	 * Updates tab icon and length counter when document is changed.
	 */
	private void noteDocChange() {
		tabbedPane.setIconAt(tabbedPane.getSelectedIndex(), editedIcon);
		String text = ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport()
				.getComponent(0)).getText();
		docLengthInfo.setText(String.format("%-20d", text.length()));
	}

	/**
	 * Updates caret column, line number and size of selection written in
	 * info-bar. Enables and disables operation which work with selected text
	 * Operation is disabled if it uses selected text and no text is selected.
	 */
	private CaretListener caretListener = new CaretListener() {

		@Override
		public void caretUpdate(CaretEvent e) {
			JTextArea currentEditor = ((JTextArea) ((JScrollPane) tabbedPane.getSelectedComponent())
					.getViewport().getComponent(0));
			try {
				int lineNumber = currentEditor.getLineOfOffset(currentEditor.getCaret().getDot());
				int colNumber = currentEditor.getCaret().getDot()
						- currentEditor.getLineStartOffset(lineNumber);
				int selectionLength = Math.abs(
						currentEditor.getCaret().getDot() - currentEditor.getCaret().getMark());
				selLineInfo.setText(String.format("%-4d", lineNumber + 1));
				selColInfo.setText(String.format("%-4d", colNumber));
				selLengthInfo.setText(String.format("%-4d", selectionLength));
				if (selectionLength == 0) {
					cutAction.setEnabled(false);
					copyAction.setEnabled(false);
					toUpperCaseAction.setEnabled(false);
					toLowerCaseAction.setEnabled(false);
					toggleCaseAction.setEnabled(false);
				} else {
					cutAction.setEnabled(true);
					copyAction.setEnabled(true);
					toUpperCaseAction.setEnabled(true);
					toLowerCaseAction.setEnabled(true);
					toggleCaseAction.setEnabled(true);
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};

	/**
	 * Initializes name, short description, accelerator and mnemonic keys of
	 * all actions.
	 */
	private void createActions() {

		addEmptyDocumentAction.putValue(Action.NAME, flp.getString("new.key"));
		addEmptyDocumentAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control N"));
		addEmptyDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		addEmptyDocumentAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("new.shDesc"));

		openDocumentAction.putValue(Action.NAME, flp.getString("open.key"));
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("open.shDesc"));

		saveDocumentAction.putValue(Action.NAME, flp.getString("save.key"));
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("save.shDesc"));

		saveAsDocumentAction.putValue(Action.NAME, flp.getString("saveAs.key"));
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control alt S"));
		saveAsDocumentAction.putValue(Action.MNEMONIC_KEY,
				KeyStroke
						.getKeyStroke(KeyEvent.VK_S,
								InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK, false)
						.getKeyCode());
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("saveAs.shDesc"));

		cutAction.putValue(Action.NAME, flp.getString("cut.key"));
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("cut.shDesc"));

		copyAction.putValue(Action.NAME, flp.getString("copy.key"));
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("copy.shDesc"));

		pasteAction.putValue(Action.NAME, flp.getString("paste.key"));
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("paste.shDesc"));

		toggleCaseAction.putValue(Action.NAME, flp.getString("toggleCase.key"));
		toggleCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
		toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleCaseAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("toggleCase.shDesc"));
		toggleCaseAction.setEnabled(false);

		toLowerCaseAction.putValue(Action.NAME, flp.getString("toLowerCase.key"));
		toLowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F4"));
		toLowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		toLowerCaseAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("toLowerCase.shDesc"));
		toLowerCaseAction.setEnabled(false);

		toUpperCaseAction.putValue(Action.NAME, flp.getString("toUpperCase.key"));
		toUpperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F5"));
		toUpperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		toUpperCaseAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("toUpperCase.shDesc"));
		toUpperCaseAction.setEnabled(false);

		sortAscAction.putValue(Action.NAME, flp.getString("sortAsc.key"));
		sortAscAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("sortAsc.shDesc"));

		sortDescAction.putValue(Action.NAME, flp.getString("sortDesc.key"));
		sortDescAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("sortDesc.shDesc"));

		uniqueAction.putValue(Action.NAME, flp.getString("unique.key"));
		uniqueAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("unique.shDesc"));

		engLangAction.putValue(Action.NAME, flp.getString("en.key"));
		engLangAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("en.shDesc"));

		hrLangAction.putValue(Action.NAME, flp.getString("hr.key"));
		hrLangAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("hr.shDesc"));

		deLangAction.putValue(Action.NAME, flp.getString("de.key"));
		deLangAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("de.shDesc"));

		closeAction.putValue(Action.NAME, flp.getString("close.key"));
		closeAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		closeAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
		closeAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("close.shDesc"));

		infoAction.putValue(Action.NAME, flp.getString("info.key"));
		infoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		infoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		infoAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("info.shDesc"));

		exitAction.putValue(Action.NAME, flp.getString("exit.key"));
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		exitAction.putValue(Action.SHORT_DESCRIPTION, flp.getString("exit.shDesc"));
	}

	/**
	 * Creates menus and sets them up in main JNotepad++ window.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		LJMenu fileMenu = new LJMenu("file", flp);
		LJMenu editMenu = new LJMenu("edit", flp);
		LJMenu toolsMenu = new LJMenu("tools", flp);
		LJMenu languageMenu = new LJMenu("language", flp);
		LJMenu changeCaseMenu = new LJMenu("changeCase", flp);
		LJMenu sortMenu = new LJMenu("sort", flp);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolsMenu);
		menuBar.add(languageMenu);
		toolsMenu.add(changeCaseMenu);
		toolsMenu.add(sortMenu);

		fileMenu.add(new JMenuItem(addEmptyDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(infoAction));
		fileMenu.add(new JMenuItem(closeAction));
		fileMenu.add(new JMenuItem(exitAction));

		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));

		toolsMenu.add(new JMenuItem(uniqueAction));

		changeCaseMenu.add(new JMenuItem(toLowerCaseAction));
		changeCaseMenu.add(new JMenuItem(toUpperCaseAction));
		changeCaseMenu.add(new JMenuItem(toggleCaseAction));

		sortMenu.add(new JMenuItem(sortAscAction));
		sortMenu.add(new JMenuItem(sortDescAction));

		languageMenu.add(new JMenuItem(engLangAction));
		languageMenu.add(new JMenuItem(hrLangAction));
		languageMenu.add(new JMenuItem(deLangAction));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Creates tool bar which holds: New, Open, Save, Save As, Cut, Copy, Paste,
	 * Info, Close and Exit actions.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(addEmptyDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(infoAction));
		toolBar.add(new JButton(closeAction));
		toolBar.add(new JButton(exitAction));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Creates info bar located on bottom of JNotepad++ window. It's consisted
	 * of: character length, selected line number, selected column number,
	 * selection length and clock.
	 */
	private void createInfoBar() {
		JToolBar infoBar = new JToolBar();
		infoBar.setFloatable(false);

		docLengthInfo = new JLabel(String.format("%-20d", 0));
		selLineInfo = new JLabel(String.format("%-4d", 0));
		selColInfo = new JLabel(String.format("%-4d", 0));
		selLengthInfo = new JLabel(String.format("%-4d", 0));

		JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
		Dimension maxSeparatorSize = separator.getMaximumSize();
		maxSeparatorSize.width = 1;
		separator.setMaximumSize(maxSeparatorSize);

		infoBar.add(new JLabel("length: "));
		infoBar.add(docLengthInfo);
		infoBar.add(separator);
		infoBar.add(new JLabel(" Ln: "));
		infoBar.add(selLineInfo);
		infoBar.add(new JLabel("Col: "));
		infoBar.add(selColInfo);
		infoBar.add(new JLabel("Sel: "));
		infoBar.add(selLengthInfo);

		clock = new JNotepadClock();
		infoBar.add(Box.createHorizontalGlue());
		infoBar.add(clock);

		this.getContentPane().add(infoBar, BorderLayout.PAGE_END);
	}

	/**
	 * Starts JNotepad++ application.
	 * 
	 * @param args No arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});

	}
}
