/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FrTestModule } from '../../../test.module';
import { InmuebleMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/inmueble-my-suffix/inmueble-my-suffix-detail.component';
import { InmuebleMySuffixService } from '../../../../../../main/webapp/app/entities/inmueble-my-suffix/inmueble-my-suffix.service';
import { InmuebleMySuffix } from '../../../../../../main/webapp/app/entities/inmueble-my-suffix/inmueble-my-suffix.model';

describe('Component Tests', () => {

    describe('InmuebleMySuffix Management Detail Component', () => {
        let comp: InmuebleMySuffixDetailComponent;
        let fixture: ComponentFixture<InmuebleMySuffixDetailComponent>;
        let service: InmuebleMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [InmuebleMySuffixDetailComponent],
                providers: [
                    InmuebleMySuffixService
                ]
            })
            .overrideTemplate(InmuebleMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InmuebleMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InmuebleMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InmuebleMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.inmueble).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
