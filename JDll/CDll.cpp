#include <stdio.h>
#include <string>

#if 0

void __declspec(dllexport) _stdcall showVoidContent()
{

}

std::string __declspec(dllexport) _stdcall showReturnContent()
{
	return std::string("11111111111111111");
}

{
	return std::string("one: ");
	//+ one +std::string("; two: ") + two;
};

{
	return one + two + atof(three.c_str());
}
#endif