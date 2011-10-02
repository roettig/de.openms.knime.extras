package de.openms.knime.extras.demanglers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.ballproject.knime.base.mime.MIMEFileCell;
import org.ballproject.knime.base.mime.demangler.Demangler;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.knime.chem.types.SdfCellFactory;
import org.knime.core.data.DataCell;
import org.knime.core.data.DataType;

import de.openms.OpenMS.knime.nodes.mimetypes.idXMLFileCell;
import de.openms.knime.extras.types.idXMLEntryCell;

public class idXMLDemangler implements Demangler
{

	@Override
	public DataType getSourceType()
	{
		return DataType.getType(idXMLFileCell.class);
	}

	@Override
	public DataType getTargetType()
	{
		return DataType.getType(idXMLEntryCell.class);
	}

	@Override
	public Iterator<DataCell> demangle(MIMEFileCell cell)
	{

		return new idXMLFileDemanglerDelegate(cell.getData());
	}

	@Override
	public MIMEFileCell mangle(Iterator<DataCell> iter)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private static class idXMLFileDemanglerDelegate implements Iterator<DataCell>
	{
		private byte[] data;
		private BufferedReader br;
		private Document doc;
		private Element  root;
		private Iterator iter;
		private List<Node> hits;
		
		public idXMLFileDemanglerDelegate(byte[] data)
		{
			this.data = data;
			InputStream in   = new ByteArrayInputStream(this.data);
			SAXReader reader = new SAXReader();
	        try
			{
				doc = reader.read(in);
			} 
	        catch (DocumentException e)
			{
				e.printStackTrace();
				throw new RuntimeException("could not read from input stream");
			}
	        root = (Element) doc.selectSingleNode("/IdXML/IdentificationRun");
	        //iter = root.elementIterator("PeptideIdentification");
	        hits = doc.selectNodes("//PeptideHit");
	        iter = hits.iterator();
		}
		
		@Override
		public boolean hasNext()
		{
			return iter.hasNext();
		}

		private Random rng = new Random(2);
		
		@Override
		public DataCell next()
		{
			Node n = (Node) iter.next();
			String sequence = n.valueOf("@sequence");
			System.out.println(sequence);
			String score    = n.valueOf("@score");
			System.out.println(score);
			double dscore = rng.nextDouble();
			try
			{
				dscore = Double.parseDouble(score);
			}
			catch(NumberFormatException e)
			{
				
			}
			return idXMLEntryCell.makeEntry(sequence, dscore);
		}

		@Override
		public void remove()
		{
			// NOP
		}
	}

	public static void main(String[] args) throws IOException
	{
		FileReader     in = new FileReader("/home/roettig/Desktop/IDMapper_1.idXML");
		BufferedReader br = new BufferedReader(in);
		String line = "";
		StringBuffer sb = new StringBuffer();
		while((line=br.readLine())!=null)
		{
			sb.append(line+"\n");
		}
		String cont = sb.toString();
		Iterator<DataCell> iter = new idXMLFileDemanglerDelegate(cont.getBytes());
		while(iter.hasNext())
		{
			idXMLEntryCell ec = (idXMLEntryCell) iter.next();
			System.out.println(ec.getPeptideSequence()+" "+ec.getPeptideScore());
		}
	}
}
