package de.openms.knime.extras.types;

import org.knime.core.data.DataValue;

public interface idXMLEntryValue extends DataValue
{
	String getPeptideSequence();
	double getPeptideScore();
}
