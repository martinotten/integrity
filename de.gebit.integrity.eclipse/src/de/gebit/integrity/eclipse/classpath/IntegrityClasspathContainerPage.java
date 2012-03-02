/**
 * 
 */
package de.gebit.integrity.eclipse.classpath;

import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * A very simple configuration page to make the Integrity Classpath Container show up in the libraries to add by the
 * user. The page is actually empty, besides some text, since there's nothing to configure anyway!
 * 
 * @author Rene Schneider
 * 
 */
public class IntegrityClasspathContainerPage extends WizardPage implements IClasspathContainerPage {

	/**
	 * Default constructor for instantiation in Eclipse.
	 */
	public IntegrityClasspathContainerPage() {
		super("Integrity Test Runner", "Integrity Test Runner", null);

		setDescription("Add the Integrity Test Runner as well as all dependencies required for execution to the classpath.");
	}

	@Override
	public void createControl(Composite aParent) {
		Composite tempComposite = new Composite(aParent, SWT.NULL);

		setControl(tempComposite);
	}

	@Override
	public boolean finish() {
		return true;
	}

	@Override
	public IClasspathEntry getSelection() {
		return JavaCore.newContainerEntry(new Path(IntegrityClasspathContainerInitializer.PATH));
	}

	@Override
	public void setSelection(IClasspathEntry aClasspathEntry) {
	}

}
