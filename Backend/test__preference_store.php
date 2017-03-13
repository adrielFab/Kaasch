<?php
require_once(preference_store.php);

class PreferenceStoreTest extends PHPUnit_Framework_TestCase
{

	public function testIfValidInputPreferences()
	{
		$this->assertInternalType('int', $wantsPet);
		$this->assertInternalType('int', $wantsSmoking);
		$this->assertInternalType('int', $wantsFemale);
		$this->assertInternalType('int', $wantsMale);
		$this->assertInternalType('string', $email);
	}

	public function testInsertNewData()
	{
		$validateResultSet = $dbC->query("SELECT * FROM Prefences WHERE Email LIKE '$email'");
		while($row = $validateResultSet->fetch_assoc())
		{
			assertTrue($row['wantsPet'] == $wantsPet &&
						$row['wantsSmoking'] == $wantsSmoking &&
						$row['wantsFemale'] == $wantsFemale &&
						$row['wantsMale'] == $wantsMale);
		}
	}
}
?>