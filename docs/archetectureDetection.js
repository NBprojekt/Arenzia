
function get_bits_system_architecture() {
    var _to_check = [] ;
    if ( window.navigator.cpuClass ) _to_check.push( ( window.navigator.cpuClass + "" ).toLowerCase() ) ;
    if ( window.navigator.platform ) _to_check.push( ( window.navigator.platform + "" ).toLowerCase() ) ;
    if ( navigator.userAgent ) _to_check.push( ( navigator.userAgent + "" ).toLowerCase() ) ;

    var _64bits_signatures = [ "x86_64", "x86-64", "Win64", "x64;", "amd64", "AMD64", "WOW64", "x64_64", "ia64", "sparc64", "ppc64", "IRIX64" ] ;
    var _bits = 32, _i, _c ;
    outer_loop:
    for( var _c = 0 ; _c < _to_check.length ; _c++ )
    {
        for( _i = 0 ; _i < _64bits_signatures.length ; _i++ )
        {
            if ( _to_check[_c].indexOf( _64bits_signatures[_i].toLowerCase() ) != -1 )
            {
               _bits = 64 ;
               break outer_loop;
            }
        }
    }
    return _bits ; 
} 
function is_32bits_architecture() { return get_bits_system_architecture() == 32 ? 1 : 0 ; }
function is_64bits_architecture() { return get_bits_system_architecture() == 64 ? 1 : 0 ; }   
