/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FrTestModule } from '../../../test.module';
import { TipoInmuebleMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix-detail.component';
import { TipoInmuebleMySuffixService } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix.service';
import { TipoInmuebleMySuffix } from '../../../../../../main/webapp/app/entities/tipo-inmueble-my-suffix/tipo-inmueble-my-suffix.model';

describe('Component Tests', () => {

    describe('TipoInmuebleMySuffix Management Detail Component', () => {
        let comp: TipoInmuebleMySuffixDetailComponent;
        let fixture: ComponentFixture<TipoInmuebleMySuffixDetailComponent>;
        let service: TipoInmuebleMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [TipoInmuebleMySuffixDetailComponent],
                providers: [
                    TipoInmuebleMySuffixService
                ]
            })
            .overrideTemplate(TipoInmuebleMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoInmuebleMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoInmuebleMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TipoInmuebleMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tipoInmueble).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
