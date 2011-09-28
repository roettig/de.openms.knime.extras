package de.openms.knime.extras.types;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataValue;


public class idXMLEntryCell extends DataCell implements idXMLEntryValue 
{
	private String sequence;
	private double score;
		
	public static idXMLEntryCell makeEntry(String sequence, double score)
	{
		return new idXMLEntryCell(sequence,score);
	}
	
	public idXMLEntryCell(String sequence, double score)
	{
		this.sequence = sequence;
		this.score     = score;
	}
	
	@Override
	protected boolean equalsDataCell(DataCell arg0)
	{
		return false;
	}

	@Override
	public int hashCode()
	{
		return 0;
	}

	@Override
	public String toString()
	{
		return String.format("[%s] score: %e",sequence,score);
	}

	@Override
	public String getPeptideSequence()
	{
		return sequence;
	}

	@Override
	public double getPeptideScore()
	{
		return score;
	}

	public static final Class<? extends DataValue>
    getPreferredValueClass() 
    {
		return idXMLEntryValue.class;
    }

}
