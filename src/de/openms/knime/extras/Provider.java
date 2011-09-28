package de.openms.knime.extras;

import java.util.ArrayList;
import java.util.List;

import org.ballproject.knime.base.mime.demangler.Demangler;
import org.ballproject.knime.base.mime.demangler.DemanglerProvider;

import de.openms.knime.extras.demanglers.idXMLDemangler;

public class Provider implements DemanglerProvider
{

	@Override
	public List<Demangler> getDemanglers()
	{
		List<Demangler> ret = new ArrayList<Demangler>();
		ret.add(new idXMLDemangler());
		return ret;
	}

}
